;; FYI, previous example doesn't always work:
;;
(map first (tree-seq next rest '((1 2 (3)) (4))))
;; => ((1 2 (3)) 4)
