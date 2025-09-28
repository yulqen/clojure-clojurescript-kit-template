(ns yulqen.guestbook.web.controllers.health
  (:require
    [ring.util.http-response :as http-response])
  (:import
    [java.util Date]))

(defn get-messages
        [req]
      (http-response/ok
       {:messages [{:message "Hello, World!"
                    :age 10
                    :heat "hot"}
                   {:message "This is a sample message"
                    :age 5
                    :heat "warm"}
                   {:message "Clojure is awesome!"
                    :age 2
                    :heat "cool"}
                   {:message "Have a great day!"
                    :age 1
                    :heat "mild"}]}))

(defn healthcheck!
      [req]
      (http-response/ok
       {:time     (str (Date. (System/currentTimeMillis)))
                  :up-since (str (Date. (.getStartTime (java.lang.management.ManagementFactory/getRuntimeMXBean))))
                  :app      {:status  "up"
                                      :message ""}}))
