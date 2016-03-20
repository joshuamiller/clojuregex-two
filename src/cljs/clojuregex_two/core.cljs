(ns clojuregex-two.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(defonce app-state (r/atom {:regex ""
                            :test-string ""}))

(defn submit-regex []
  (let [regex (.-value (.getElementById js/document "regex"))
        string (.-value (.getElementById js/document "sample"))]
    (swap! app-state assoc :regex regex :test-string string)))

(defn cljs-results []
  [:ol
   (for [res (re-seq (re-pattern (:regex @app-state)) (:test-string @app-state))]
     [:li res])])

(defn regex-input []
  [:div
   [:label {:for "regex"} "Regular Expression"]
   [:textarea {:name "regex" :placeholder "regex" :id "regex"}
    (:regex @app-state)]])

(defn sample-string-input []
  [:div
   [:label {:for "sample"} "Test String"]
   [:textarea {:name "sample" :placeholder "sample" :id "sample"}
    (:test-string @app-state)]
   [:div {:class "submit"}
    [:p
     [:button {:id "submit"
               :on-click submit-regex} "Test Regex"]]]])

(r/render [cljs-results] (.getElementById js/document "cljs"))
(r/render [regex-input] (.getElementById js/document "for-regex"))
(r/render [sample-string-input] (.getElementById  js/document "for-sample"))
