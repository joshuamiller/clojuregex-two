(ns clojuregex-two.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [clojuregex-two.core-test]))

(enable-console-print!)

(doo-tests 'clojuregex-two.core-test)
