(ns test
  (:use compojure))

(defn html-doc
  [title & body]
  (html
    (doctype :html4)
    [:html
      [:head
        [:title title]]
      [:body
        [:div
          [:h2
            [:a {:href "/add"} "Add" ]]
          [:h2
            [:a {:href "/multiply"} "Multiply" ]]]
          body]]))

(def sum-form
  (html-doc "Sum"
    (form-to [:post "/add"]
      (text-field {:size 10} :x)
      [:select
        (select-options
          ["+", "x"])]
      (text-field {:size 10} :y)
    (submit-button "="))))

(def product-form
  (html-doc "Multiply"
    (form-to [:post "/multiply"]
      (text-field {:size 10} :x)
      "x"
      (text-field {:size 10} :y)
      (submit-button "="))))

(defn add-result
  [x y]
  (let [x (Integer/parseInt x)
        y (Integer/parseInt y)]
    (html-doc "Result"
      x " + " y " = " (+ x y))))

(defn multiply-result
  [x y]
  (let [x (Integer/parseInt x)
        y (Integer/parseInt y)]
    (html-doc "Result"
      x " x " y " = " (* x y))))

(defroutes webservice
  (GET "/"
    (html
      (doctype :html5)
      [:html
        [:body
          [:h1
            [:a {:href "/add"} "Add Some Numbers" ]]
          [:h1
            [:a {:href "/multiply"} "Multiply Some Numbers" ]]]]))

  (GET "/add"
    sum-form)
  (POST "/add"
    (add-result (params :x) (params :y)))

  (GET "/multiply"
    product-form)
  (POST "/multiply"
    (multiply-result (params :x) (params :y))))

(run-server {:port 8080}
  "/*" (servlet webservice))