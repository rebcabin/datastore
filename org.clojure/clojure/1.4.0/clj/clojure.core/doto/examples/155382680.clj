;; Be careful when calling 'dotimes' from within a 'doto' statement
(doto (java.util.ArrayList.)
  (.add -2)
  (.add -1)
  (dotimes [i 3] (.add i)))
;; => java.lang.IllegalArgumentException: dotimes requires a vector for its binding (NO_SOURCE_FILE:1)

(macroexpand
 '(doto (java.util.ArrayList.)
    (.add -2)
    (.add -1)
    (dotimes [i 3] (.add i))))

;; => (let* [G__15339 (java.util.ArrayList.)]
;;      (.add G__15339 -2)
;;      (.add G__15339 -1)
;;      (dotimes G__15339 [i 3]
;;        (.add i)) G__15339)

;; what has happened is that (java.util.ArrayList.) has secretly become the first argument to
;; 'dotimes' and thus the exception informs us that it can't find the binding vector required for
;; 'dotimes' to expand. You can cure this behaviour by simply using 'do' instead of 'doto' or by
;; wrapping the call to 'dotimes' in a function. e.g: using 'let' with implicit 'do' instead of
;; 'doto'

(let [al (java.util.ArrayList.)]
  (.add al -2)
  (.add al -1)
  (dotimes [i 3] (.add al i))
  al) ;; return the ArrayList
#<ArrayList [-2, -1, 0, 1, 2] >
;; exactly what we intended

;; Another option is to wrap 'dotimes' in a function literal
(doto (java.util.ArrayList.)
  (.add -2)
  (.add -1)
  (#(dotimes [i 3] (.add % i))))
#<ArrayList [-2, -1, 0, 1, 2] >
;; exactly what we intended again
