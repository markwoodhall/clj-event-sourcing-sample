(ns clj-event-sourcing-sample.frequent-flier-account
  (:use [clj-event-sourcing-sample.events])
  (:import [clj_event_sourcing_sample.events PromotedToGoldStatus]
           [clj_event_sourcing_sample.events StatusMatched]  
           [clj_event_sourcing_sample.events FrequentFlierAccountCreated]  
           [clj_event_sourcing_sample.events FlightTaken]))

(defrecord FrequentFlierAccount 
  [id miles tier-points status expected-version changes])

(defmulti transition 
  (fn [ff event]
    (class event)))

(defmethod transition FlightTaken
  [frequent-flier event]
  (-> frequent-flier 
      (update-in [:miles] + (:miles-added event))
      (update-in [:tier-points] + (:tier-points-added event))))

(defmethod transition PromotedToGoldStatus
  [frequent-flier event]
  (assoc frequent-flier :status :gold))

(defmethod transition StatusMatched
  [frequent-flier event]
  (assoc frequent-flier :status (:new-status event)))

(defmethod transition FrequentFlierAccountCreated
  [frequent-flier event]
  (-> frequent-flier
      (assoc :id (:account-id event))
      (assoc :tier-points (:opening-tier-points event))  
      (assoc :status :red)  
      (assoc :miles (:opening-miles event))))

(defn track
  [frequent-flier event]
  (transition (update-in frequent-flier [:changes] conj event) event))

(defn record-flight-taken
  [frequent-flier miles tier-points]
  (let [flight-taken (->FlightTaken miles tier-points)
        latest (track frequent-flier flight-taken)]
    (if (and (> (:tier-points latest) 20)
             (not= (:status latest) :gold))
      (track latest (->PromotedToGoldStatus))
      latest)))

(defn new-frequent-flier-from-history
  [history]
  (let [ff (->FrequentFlierAccount "" 0 0 :red 0 history)]
    (assoc (reduce transition ff history) :expected-version (count history))))

(comment 
  (let [ff (new-frequent-flier-from-history [(->FrequentFlierAccountCreated "1" 100 1)])]
    (record-flight-taken ff 1000 21)))
