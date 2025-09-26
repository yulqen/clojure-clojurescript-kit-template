(ns yulqen.guestbook.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

;; -------------------------
;; Views

(defn table []
      [:table
       [:thead
        [:tr
         [:th "Big name"]
         [:th "Big Age"]]]
       [:tbody
        [:tr
         [:td "Alice"]
         [:td 30]]
        [:tr
         [:td "Bob"]
         [:td 25]]]])

(defn home-page []
      [:div
       [:div [:h3.text-3xl.font-bold "Welcome to ClojureScript SPA - this is ace"]]
       [:div [:p {:class "bg-green-400 m-2"} "This is Javascript free ClojureScript SPA!"]][table]])


;; -------------------------
;; Initialize app

(defn ^:dev/after-load mount-root []
      (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export ^:dev/once init! []
  (mount-root))
