(ns clojuregex-two.core
  (:require [ajax.core :refer [GET]]
            [ajax.edn :refer [edn-response-format]]
            [cemerick.url :refer [url-encode]]
            [reagent.core :as r]))

(enable-console-print!)

(defonce app-state (r/atom {:regex ""
                            :test-string ""
                            :clj-results []
                            :cljs-results []}))

(defn get-clj-results []
  (GET "regex" {:response-format (edn-response-format)
                :params {:regex (url-encode (:regex @app-state))
                         :sample (:test-string @app-state)}
                :handler #(swap! app-state assoc :clj-results %)}))

(defn submit-regex []
  (let [regex (.-value (.getElementById js/document "regex"))
        string (.-value (.getElementById js/document "sample"))]
    (swap! app-state assoc :regex regex :test-string string)
    (get-clj-results)))

(defn cljs-results []
  [:ol
   (for [res (re-seq (re-pattern (:regex @app-state)) (:test-string @app-state))]
     [:li res])])

(defn clj-results []
  [:ol
   (for [res (:clj-results @app-state)]
     [:li res])])

(defn regex-input []
  [:div
   [:label {:for "regex"} "Regular Expression"]
   [:textarea {:name "regex"
               :placeholder "regex"
               :id "regex"
               :default-value (:regex @app-state)}]
   [:p
    [:button {:id "copy" :data-clipboard-target "#regex"}
     "Copy To Clipboard"]]])

(defn sample-string-input []
  [:div
   [:label {:for "sample"} "Test String"]
   [:textarea {:name "sample"
               :placeholder "sample"
               :id "sample"
               :default-value (:test-string @app-state)}]
   [:div {:class "submit"}
    [:p
     [:button {:id "submit"
               :on-click submit-regex} "Test Regex"]]]])

(r/render [cljs-results] (.getElementById js/document "cljs"))
(r/render [clj-results] (.getElementById js/document "clj"))
(r/render [regex-input] (.getElementById js/document "for-regex"))
(r/render [sample-string-input] (.getElementById  js/document "for-sample"))
