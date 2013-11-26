(require 'cc-mode)

;; These are only required at compile time to get the sources for the
;; language constants.	(The cc-fonts require and the font-lock
;; related constants could additionally be put inside an
;; (eval-after-load "font-lock" ...) but then some trickery is
;; necessary to get them compiled.)
(eval-when-compile
	(require 'cc-langs)
	(require 'cc-fonts))

(eval-and-compile
	(c-add-language 'lpc-mode 'c-mode))

(defconst lpc-magic-quote-comma	 '(9))
(defconst lpc-magic-symbol-name	 '(3))

;;;

(c-lang-defconst c-make-mode-syntax-table
  lpc `(lambda ()
       (let ((table (make-syntax-table)))
	 (c-populate-syntax-table table)
		 (modify-syntax-entry ?' "'" table)
	 table)))

(c-lang-defconst c-has-bitfields
  lpc t)

(c-lang-defconst c-symbol-start
  lpc    (concat "\\(?:[" c-alpha "_]\\|#'\\)"))

(c-lang-defconst c-symbol-chars
  lpc    (concat c-alnum "_$#'"))

(c-lang-defconst c-identifier-ops
  lpc  '((right-assoc "::")
	 (prefix "::")))

(c-lang-defconst c-after-id-concat-ops
  lpc    nil)

(c-lang-defconst c-string-escaped-newlines
  lpc t)

(c-lang-defconst c-multiline-string-start-char
  lpc    nil)

;;; Operators

(c-lang-defconst c-assignment-operators
  lpc (append (c-lang-const c-assignment-operators c)
	       '("||=" "&&=")))

(c-lang-defconst c-operators
  lpc `(
	(prefix "#")
	(left-assoc "##")
	(prefix "'" "#'")
	(left-assoc "." "->")
	(postfix "++" "--" "[" "]" "(" ")")
	(prefix "++" "--" "+" "-" "!" "~")
	(left-assoc "*" "/" "%")
	(left-assoc "+" "-")
	(left-assoc "<<" ">>")
	(left-assoc "<" ">" "<=" ">=")
	(left-assoc "==" "!=")
	(left-assoc "&")
	(left-assoc "^")
	(left-assoc "|")
	(left-assoc "&&")
	(left-assoc "||")
	(right-assoc-sequence "?" ":")
	(right-assoc ,@(c-lang-const c-assignment-operators))
	(left-assoc ",")))
		 
(c-lang-defconst c-overloadable-operators
  lpc '("#'+" "#'-" "#'&" "#'&&" "#'|" "#'||" "#'^" "#'<<" "#'>>" "#'*" "#'/" "#'%" "#'~"
	"#'==" "#'!=" "#'<" "#'>" "#'!" "#'[]" "#'[..]" "#'[.." "#'[<..<]" "#'({" "#'([" "#'([,]" "#'->" "#'+"))

(c-lang-defconst c-opt-op-identifier-prefix
lpc nil)

(c-lang-defconst c-other-op-syntax-tokens
  lpc '("{" "}" "(" ")" "[" "]" ";" ":" "," "=" "/*" "*/" "//" "(:" ":)" "#" "##" "::" "..." "'"))

;;; Keywords

(c-lang-defconst c-primitive-type-kwds
	lpc '("int" "status" "long"
	"float" "array" "mapping"
	"object" "closure" "descriptor"
	"symbol" "string" "mixed"
	"void" ))

(c-lang-defconst c-primitive-type-prefix-kwds
  lpc '("long" "short" "signed" "unsigned"))

(c-lang-defconst c-typedef-kwds
lpc nil)

(c-lang-defconst c-type-prefix-kwds
  lpc nil)

(c-lang-defconst c-type-modifier-kwds
lpc nil)

(c-lang-defconst c-class-decl-kwds
  lpc nil)

(c-lang-defconst c-brace-list-decl-kwds
  lpc nil)

(c-lang-defconst c-other-block-decl-kwds
  lpc nil)

(c-lang-defconst c-modifier-kwds
  lpc '("nomask" "nosave" "virtual" "static" "varargs" ))

(c-lang-defconst c-other-decl-kwds
  lpc '("inherit"))

(c-lang-defconst c-decl-start-kwds
lpc nil)

(c-lang-defconst c-decl-hangon-kwds
  lpc nil)

(c-lang-defconst c-protection-kwds
  lpc '("private" "protected" "public" "internal"))

(c-lang-defconst c-block-decls-with-vars
  lpc nil)

(c-lang-defconst c-postfix-decl-spec-kwds
  lpc    nil)

(c-lang-defconst c-nonsymbol-sexp-kwds
  lpc nil)

(c-lang-defconst c-type-list-kwds
  lpc '("inherit"))

(c-lang-defconst c-ref-list-kwds
lpc nil)

(c-lang-defconst c-colon-type-list-kwds
lpc nil)

(c-lang-defconst c-paren-nontype-kwds
  lpc nil)

(c-lang-defconst c-paren-type-kwds
lpc nil)

(c-lang-defconst c-<>-type-kwds
lpc nil)

(c-lang-defconst c-<>-arglist-kwds
lpc nil)

(c-lang-defconst c-<>-sexp-kwds
lpc nil)

(c-lang-defconst c-brace-id-list-kwds
  lpc (c-lang-const c-brace-list-decl-kwds))

(c-lang-defconst c-block-stmt-1-kwds
lpc '("do" "else"))

(c-lang-defconst c-block-stmt-2-kwds
	lpc '("for" "if" "unless" "switch" "while" "foreach"))

(c-lang-defconst c-simple-stmt-kwds
	lpc '("break" "continue" "return"))

(c-lang-defconst c-paren-stmt-kwds
  lpc    '("for"))

(c-lang-defconst c-asm-stmt-kwds
  lpc nil)

(c-lang-defconst c-case-kwds
lpc '("case"))

(c-lang-defconst c-label-kwds
  lpc '("case" "default"))

(c-lang-defconst c-before-label-kwds
  lpc '("break" "continue"))

(c-lang-defconst c-constant-kwds
lpc '("Null" "True" "False"))

(c-lang-defconst c-primary-expr-kwds
lpc '("this"))

(c-lang-defconst c-lambda-kwds
  lpc '("lambda" "unbound_lambda"))

(c-lang-defconst c-inexpr-block-kwds
lpc nil)

(c-lang-defconst c-inexpr-class-kwds
lpc nil)

(c-lang-defconst c-inexpr-brace-list-kwds
lpc nil)

(c-lang-defconst c-bitfield-kwds
  lpc '("char" "int" "long" "signed" "unsigned"))

(c-lang-defconst c-other-kwds
lpc nil)

;;;
;;; Additional constants for parser-level constructs.

(c-lang-defconst c-decl-prefix-re
  lpc "\\([\{\}\(\);,]+\\)")

(c-lang-defconst c-special-brace-lists
  lpc '((?{ . ?}) (?\[ . ?\])))

(c-lang-defconst c-recognize-knr-p
  lpc nil)

(c-lang-defconst c-recognize-typeless-decls
  lpc t)

(c-lang-defconst c-recognize-<>-arglists
lpc nil)

(c-lang-defconst c-enums-contain-decls
  lpc nil)

(c-lang-defconst c-recognize-paren-inits
  lpc nil)

(c-lang-defconst c-recognize-paren-inexpr-blocks
  lpc t)

(c-lang-defconst c-recognize-colon-labels
  lpc nil)

;;;

(defconst lpc-font-lock-keywords-1 (c-lang-const c-matchers-1 lpc)
  "Minimal font locking for C mode.
Fontifies only preprocessor directives (in addition to the syntactic
fontification of strings and comments).")

(defconst lpc-font-lock-keywords-2 (c-lang-const c-matchers-2 lpc)
  "Fast normal font locking for C mode.
In addition to `c-font-lock-keywords-1', this adds fontification of
keywords, simple types, declarations that are easy to recognize, the
user defined types on `c-font-lock-extra-types', and the doc comment
styles specified by `c-doc-comment-style'.")

(defconst lpc-font-lock-keywords-3 (c-lang-const c-matchers-3 lpc)
  "Accurate normal font locking for C mode.
Like `c-font-lock-keywords-2' but detects declarations in a more
accurate way that works in most cases for arbitrary types without the
need for `c-font-lock-extra-types'.")

;; (defvar lpc-font-lock-keywords lpc-font-lock-keywords-3
;;   "Default expressions to highlight in C mode.")

(defun lpc-modify-syntax-at (beg end syntax)
	"Apply a syntax-property value syntax from beg to end."
	(if (<= (point-max) end) nil; noop
		(progn
			(put-text-property beg			end 'syntax-table	 syntax)
			(put-text-property (1- end) end 'rear-nonsticky t		 ))))

(defvar lpc-mode-syntax-table nil
  "Syntax table used in lpc-mode buffers.")
(or lpc-mode-syntax-table
    (setq lpc-mode-syntax-table
	  (funcall (c-lang-const c-make-mode-syntax-table lpc))))

(defvar lpc-mode-abbrev-table nil
	"Abbreviation table used in lpc-mode buffers.")
(c-define-abbrev-table 'lpc-mode-abbrev-table
;; 	;; Keywords that if they occur first on a line might alter the
;; syntactic context, and which therefore should trig reindentation
;; when they are completed.
	'(("else" "else" c-electric-continued-statement 0)
		("while" "while" c-electric-continued-statement 0)
		("catch" "catch" c-electric-continued-statement 0)))


;; (defun lpc-maybe-electric-brace (arg)
;; 	"Insert character and maybe correct line's indentation."
;; 	(interactive "P")
;; 	(if (= last-command-char ?{) (= last-command-char ?\[) (= last-command-char ?:)
;; 		(if (= (preceding-char) ?\()
;; 			(self-insert-command (prefix-numeric-value arg))
;; 			(c-electric-brace arg))
;; 		;; (= last-command-char ?})
;; 		(let (start-point state containing-sexp)
;; 			(save-excursion (beginning-of-defun)
;; 				(setq start-point (point)))
;; 			(save-excursion (setq state (parse-partial-sexp (point) start-point 0)))
;; 			(setq containing-sexp (car (cdr state)))
;; 			(if (and containing-sexp (save-excursion
;; 				(goto-char (1- containing-sexp))
;; 				(looking-at "(")))
;; 					(progn
;; 						(self-insert-command (prefix-numeric-value arg))
;; 						(lpc-scan-magic-quote))
;; 				(c-electric-brace arg)))))

(defconst lpc-magic-quote-regex "({\\s-*`\\([^\\s-\n,}]+\\|,\\)\\s-*[,}]")

;; (defun lpc-magic-comma-p (pt)
;; 	(let ((bol nil) (eol nil) (pos nil) (ret nil))
;; 		(save-excursion
;; 			(goto-char pt)
;; 			(end-of-line)
;; 			(setq eol (point))
;; 			(beginning-of-line)
;; 			(setq bol (point))
;; 			(while (and (not ret)
;; 					(setq pos (re-search-forward lpc-magic-quote-regex eol t)))
;; 				(if (/= (1- pos) pt) nil
;; 					(setq ret (list (- (match-beginning 1) 1)
;; 						(match-beginning			 1)
;; 						(match-end						 1)
;; 						bol)) ) )) ret))

(defun lpc-scan-magic-quotes ()
	(save-excursion
		(let ((qpos nil) (wbeg nil) (wend nil))
			(while (re-search-forward lpc-magic-quote-regex nil t)
				(setq qpos (+ (match-beginning 0) 3)
					wbeg (match-beginning			 1)
					wend (match-end						 1))
				(lpc-modify-syntax-at qpos (1+ qpos) lpc-magic-quote-comma)
				(lpc-modify-syntax-at wbeg wend			lpc-magic-symbol-name)
				)
			)
		)
	)

;; (defun lpc-scan-magic-quote ()
;; 	(save-excursion
;; 		(let ((coord nil) (qpos nil) (wbeg nil) (wend nil) (bol nil))
;; 			(if (setq coord (lpc-magic-comma-p (1- (point))))
;; 				(progn
;; 					(setq qpos	(car				 coord)
;; 						wbeg	(cadr				coord)
;; 						wend	(car	(cddr coord))
;; 						bol	 (cadr (cddr coord)))
;; 					(lpc-modify-syntax-at qpos (1+ qpos) lpc-magic-quote-comma)
;; 					(lpc-modify-syntax-at wbeg wend			lpc-magic-symbol-name)
;; 					(font-lock-fontify-region bol wend) )
;; 			)
;; 		)
;; 	)
;; )

;; (defun lpc-maybe-quote-ref (arg)
;; 	"Kludge to work around multiple syntactic meanings of #',' #'[' et al in LPC."
;; 	(interactive "P")
;; 	(self-insert-command (prefix-numeric-value arg))
;; 	(lpc-scan-magic-quote) )

(defvar lpc-mode-map ()
  "Keymap used in lpc-mode buffers.")
(if lpc-mode-map
    nil
  (setq lpc-mode-map (c-make-inherited-keymap))
	(define-key lpc-mode-map "\C-lpc"		'c-scope-operator))

;;	(define-key lpc-mode-map "{"				'lpc-maybe-electric-brace)
;;	(define-key lpc-mode-map "}"				'lpc-maybe-electric-brace)
;;	(define-key lpc-mode-map ":"				'lpc-maybe-electric-brace)
;;	(define-key lpc-mode-map ","				'lpc-maybe-quote-ref)
;;	(define-key lpc-mode-map "\C-c\C-e" 'c-macro-expand))

(easy-menu-define c-lpc-menu lpc-mode-map "lpc Mode Commands"
		  (cons "Lpc" (c-lang-const c-mode-menu lpc)))


;; bring it all together:


;;;###autoload

;; (define-derived-mode lpc-mode pike-mode "Lpc"
;;   "Major mode for editing Lpc code.
;; To submit a problem report, enter `\\[c-submit-bug-report]' from a
;; lpc-mode buffer.  This automatically sets up a mail buffer with
;; version information already added.  You just need to add a description
;; of the problem, including a reproducible test case, and send the
;; message.

;; To see what version of CC Mode you are running, enter `\\[c-version]'.

;; The hook `c-mode-common-hook' is run with no args at mode
;; initialization, then `lpc-mode-hook'.

;; Key bindings:
;; \\{lpc-mode-map}"
;;   (c-initialize-cc-mode t)
;;   (set-syntax-table lpc-mode-syntax-table)
;;   (setq local-abbrev-table lpc-mode-abbrev-table
;; 	abbrev-mode t)
;;   (use-local-map lpc-mode-map)
;;   (c-init-language-vars 'lpc-mode)
;;   (c-common-init 'lpc-mode)
;;   (easy-menu-add c-lpc-menu)
;; ;;  (cc-imenu-init cc-imenu-lpc-generic-expression) ;TODO
;;   (c-run-mode-hooks 'c-mode-common-hook 'lpc-mode-hook)
;;   (c-update-modeline))


(defun lpc-mode ()
 	"Major mode for editing lpc code.

The hook #'c-mode-common-hook' is run with no args at mode
initialization, then #'lpc-mode-hook'.

Key bindings:
\\{lpc-mode-map}"
	(interactive)
	(kill-all-local-variables)
	(c-initialize-cc-mode t)
	(set-syntax-table lpc-mode-syntax-table)
	(setq major-mode 'lpc-mode
	mode-name "lpc"
	local-abbrev-table lpc-mode-abbrev-table
	abbrev-mode t)
	(use-local-map c-mode-map)
	;; #'c-init-language-vars' is a macro that is expanded at compile
	;; time to a large #'setq' with all the language variables and their
	;; customized values for our language.
	(c-init-language-vars lpc-mode)
	;; #'c-common-init' initializes most of the components of a CC Mode
	;; buffer, including setup of the mode menu, font-lock, etc.
	;; There's also a lower level routine #'c-basic-common-init' that
	;; only makes the necessary initialization to get the syntactic
	;; analysis and similar things working.
	(c-common-init 'lpc-mode)
	(easy-menu-add c-lpc-menu)
;;	(lpc-scan-magic-quotes)
	(run-hooks 'c-mode-common-hook)
	(run-hooks 'lpc-mode-hook)
	(c-update-modeline))

(provide 'lpc-mode)


