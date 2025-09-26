(ns yulqen.guestbook.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

(defn pagga-box []
      [:div {:class "p-4 border rounded-lg bg-blue-200 text-black my-4 shadow-md"}
       [:h3 "PAGGA BOX"]
       [:p "This is pagga box content"]])

(defn counter []
  ;; The outer function runs once on mount
  (let [count-atom (r/atom 0)]
    ;; The inner function runs on every render
    (fn []
      [:div.flex.flex-col.items-center.my-4
       [:p.text-4xl "Count: " @count-atom]
       [:button.bg-red-500.p-1.mt-5.text-white {:on-click #(swap! count-atom inc)} "Increment Me!"]])))

(defn table []
      [:table {:class "table-auto border border-gray-400 w-full text-center shadow-md"}
              [:thead {:class "bg-gray-200 text-black"}
                      [:tr.border-slate-200
                       [:th "Big chugga wuggah woo"]
                       [:th "Big Age"]]]
              [:tbody.text-green-700
               [:tr.border.border-slate-200
                [:td.bg-gray-200 "Sten Gunn"]
                [:td 30]]
               [:tr
                [:td "Chinger lavesl"]
                [:td 25]]]])

(defn home-page []
      [:div {:class "m-6"}
            [:div [pagga-box] [counter]]
            [:div [:h3.text-4xl.font-bold "Welcome to ClojureScript SPA!"]]
            [:div [:p {:class "bg-yellow-200 text-black my-4"} "This is Javascript free ClojureScript SPA!"]][table]])


;; -------------------------
;; Initialize app

(defn ^:dev/after-load mount-root []
      (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export ^:dev/once init! []
  (mount-root))
