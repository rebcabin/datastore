(zero? 0)
;; => true

(zero? 0.0)
;; => true

(zero? 1)
;; => false

(zero? 0x0)
;; => true

(zero? 3.14159265358M)
;; => false

(zero? (/ 1 2))
;; => false
