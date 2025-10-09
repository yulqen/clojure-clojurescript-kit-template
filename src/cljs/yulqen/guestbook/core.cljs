 (ns yulqen.guestbook.core
   (:require
    [yulqen.guestbook.utils :as utils]
    [reagent.core :as r]
    [reagent.dom :as d]
    [ajax.core :refer [GET]]
    [cljs.pprint :as pprint]))

 (comment
   (ns-publics 'yulqen.guestbook.utils)
   (utils/age-background 8)
   ;; => "bg-red-400"
   (utils/heat-colours "mild")
   ;; => "bg-blue-200"
   (+ 1 2)
   (def r-core
     (ns-publics 'reagent.core))
   ,)

 
(defn pagga-box [year]
      [:div {:class "p-4 border rounded-lg bg-blue-100 text-black my-4 shadow-md"}
       [:div.flex.justify-between
        [:div
         [:p.font-bold "PAGGA BOXZ " year]]
        [:div
         [:p.flex-auto "This is pagga box contents has a million and one things..."]]]])

(defn counter []
  ;; The outer function runs once on mount
      (let [count-atom (r/atom 1002)]
        ;; The inner function runs on every render
        (fn []
            [:div.flex.flex-col.items-center.my-4
             [:p.text-4xl "Count: " @count-atom]
             [:button.bg-red-500.p-1.mt-5.text-white {:on-click #(swap! count-atom inc)} "Increment Me!"]])))

 (defn rep-2 [s]
   (apply str (repeat 2 s)))

(def table-data-state (r/atom {:status :loading
                               :messages nil}))

 (comment
   (apply str (repeat 10 "ssd"))
   (rep-2 "woo")
   ,)


(defn fetch-table-data! []
      (GET "/api/messages"
           {:handler (fn [response-body]
                         (let [message-list (:messages response-body)]
                           (swap! table-data-state assoc
                                  :status :ready
                                  :messages message-list)))
                     :error-handler (fn [error]
                                        (swap! table-data-state
                                               assoc
                                               :status :error
                                               :messages nil)
                                        (js/console "Fetch error:" error))}))



(defn message-table-view [{:keys [status messages]}]
  (case status
    :loading [:p "Loading message data"]
    :error [:p.error "Failed to load data"]
    :ready
    [:table {:class "table-auto border border-gray-200 w-full text-center"}
     [:thead.bg-green-400
      [:tr.rounded-lg
       [:th "Messages from..."]
       [:th "Ages"]
       [:th "Heat level"]]]
     [:tbody.border.border-gray-100
      (for [message messages]
        (let [age-background (utils/age-background (:age message))
              heat-colours (utils/heat-colours (:heat message))] 
          [:tr.border.border-gray-300 {:key (:message message)}
           [:td  (:message message)]
           [:td {:class age-background} (:age message)]
           [:td {:class heat-colours } (:heat message)]]))]]
    _ [:p.error "Unexpected application state!"]))

 (comment
  (message-table-view {:status :ready
                       :messages [{:message "yonk"
                                            :age 10
                                            :heat "mild"}
                                  {:message "Tinkers!"
                                            :age 110
                                            :heat "hot!"}
                                  {:message "Cookooos"
                                            :age 34
                                            :heat "cold"}]})
   ,)


(defn message-table-container []
      (r/create-class
       {:component-did-mount fetch-table-data!
                             :reagent-render
                             (fn []
                               ;; Hand off rendering to the display component
                                 [message-table-view @table-data-state])}))


(defn table []
      (let [toss (r/atom {:text "Wookka!"
                                :color "text-green-700"})]
        (fn []
            [:table {:class "table-auto border border-gray-400 w-full text-center shadow-md"}
                    [:thead {:class "bg-gray-200 text-black"}
                            [:tr.border-slate-200
                             [:th "Big chugga wuggah woo"]
                             [:th "Big Age"]
                             [:th "Small Age"]]]
                    [:tbody.text-green-700
                     [:tr.border.border-slate-200
                      [:td {:class "bg-yellow-200"
                                   :on-click #(swap! toss (fn [col] (assoc col :text "ff")))} (:text @toss)]
                      [:td "sdsd"]
                      [:td 30]]
                     [:tr
                      [:td "Chinger lavesl"]
                      [:td 25]]]])))

 (defn input-field [label-text id]
   (r/with-let [value (r/atom nil)]
     [:div
      [:label.font-bold.mb-2 "Enter some text here: " @value]
      [:input.border-2.rounded-md.border-blue-500.mb-2.p-2.w-full
       {:type "text"
        :value @value
        :on-change #(reset! value (-> % .-target .-value))}]]))


(defn home-page []
  [:div {:class "m-6"}
   [:div [:h3.text-4xl.font-bold "Welcome to ClojureScript SPA!"]]
   [:div [counter]]   
   [:div [pagga-box 1992]
              
         [input-field "Name" "name"]]
   [:div [message-table-container]]

   [:div [:p {:class "bg-yellow-200 text-black my-4"} "This is Javascript free ClojureScript SPA!"]][table]])


;; -------------------------
;; Initialize app

(defn ^:dev/after-load mount-root []
      (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export ^:dev/once init! []
  (mount-root))
