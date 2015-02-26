(ns clj-event-sourcing-sample.events)

(defrecord FrequentFlierAccountCreated
  [account-id opening-miles opening-tier-points])

(defrecord FlightTaken
  [miles-added tier-points-added])

(defrecord StatusMatched 
  [new-status])

(defrecord PromotedToGoldStatus [])
