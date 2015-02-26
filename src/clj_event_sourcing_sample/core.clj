(ns clj-event-sourcing-sample.core
  (:use [clj-event-sourcing-sample.events])
  (:use [clj-event-sourcing-sample.frequent-flier-account])
  (:import [clj_event_sourcing_sample.events StatusMatched]  
           [clj_event_sourcing_sample.events FrequentFlierAccountCreated]  
           [clj_event_sourcing_sample.events FlightTaken])
  (:gen-class))

(defn -main
  [& args]
  (let [history [(->FrequentFlierAccountCreated "1234567" 1000 0)
                 (->StatusMatched :silver)
                 (->FlightTaken 2525 5)
                 (->FlightTaken 2512 5)
                 (->FlightTaken 5600 5)
                 (->FlightTaken 3000 3)]
        aggregate (new-frequent-flier-from-history history)
        _ (println "Before RecordFlighTaken")
        _ (println aggregate)
        aggregate (record-flight-taken aggregate 1000 3)
        _ (println "After RecordFlightTaken")
        _ (println aggregate)]))
