(ns yulqen.guestbook.utils)

(defn age-background [age]
  (cond
    (and (>= age 1) (< age 5)) "bg-red-200"
    (and (>= age 5) (<= age 8)) "bg-red-400"
    (and (> age 8) (< age 11)) "bg-red-600"))

(defn heat-colours [colour]
      (cond
       (= colour "hot") "bg-red-600"
       (= colour "warm") "bg-red-300"
       (= colour "cool") "bg-blue-400"
       (= colour "mild") "bg-blue-200"
       (= colour "cold") "bg-blue-600"))
