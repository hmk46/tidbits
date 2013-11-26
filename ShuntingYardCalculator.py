import operator
import re
from collections import namedtuple

# BEGIN operator flags

# operator precedence
PREC_ADD = 0
PREC_MUL = 1
PREC_MOD = 2
PREC_EXP = 3

# associativity
ASSOC_LEFT = 0
ASSOC_RIGHT = 1

#END operator flags

# grouping tokens
GROUP_OPEN_TOKENS = ('(')
GROUP_CLOSE_TOKENS = (')')

# operators dictionary
# { <operator> : (<precedence>, <associativity>, <operator>)}
OPERATORS = {
    '+' : (PREC_ADD, ASSOC_LEFT, operator.add),
    '-' : (PREC_ADD, ASSOC_LEFT, operator.sub),
    '*' : (PREC_MUL, ASSOC_LEFT, operator.mul),
    '/' : (PREC_MUL, ASSOC_LEFT, lambda x,y: operator.truediv(y,x)), # work around denominator-first popping
    '%' : (PREC_MOD, ASSOC_LEFT, lambda x,y: operator.mod(y,x)), # workaround divisor-first popping
    '^' : (PREC_EXP, ASSOC_LEFT, lambda x,y: operator.pow(y,x)) # workaround power-first popping
    }

# evaluate an operator given its token and a tuple of arguments

def evaluate_operation(o, arg):
    if is_operator(o):
        return OPERATORS[o][2](*arg)
    else:
        raise ValueError("can't evaluate invalid operator " + o)

# operator comparison methods

def compair_precedence(o1, o2):
    if is_operator(o1) and is_operator(o2):
        return OPERATORS[o1][0] - OPERATORS[o2][0]
    else:
        raise ValueError("can't compair precedence between invalid operator " + o1 + " or " + o2)

def compair_associativity(o1, o2):
    if is_operator(o1) and is_operator(o2):
        return OPERATORS[o1][1] - OPERATORS[o2][1]
    else:
        raise ValueError("can't compair associativity between invalid operator " + o1 + " or " + o2)

# validation methods

def is_operator(o):
    return o in OPERATORS

def is_group_open_token(t):
    return t in GROUP_OPEN_TOKENS

def is_group_close_token(t):
    return t in GROUP_CLOSE_TOKENS

def is_group_token(t):
    return is_group_open_token(t) or is_group_close_token(t)

def is_numerical_token(t):
    try:
        return int(t)
        return True
    except ValueError:
        try:
            float(t)
            return True
        except ValueError:
            return False

# evaluate an rpn iterator

def evaluate_rpn(rpn_iterator):
    eval_stack = []
    for t in rpn_iterator:
        if is_operator(t):
            if len(eval_stack) < 2:
                if len(eval_stack) == 0:
                    raise ValueError("Invalid operation " + t + " on empty stack.")
                else:
                    raise ValueError("invalid operation" + t + " on stack (" + " ".join([str(i) for i in eval_stack]) + "): evaluation stack is too short")
            else:
                eval_stack.append(evaluate_operation(t, (eval_stack.pop(), eval_stack.pop())))
        else:
            eval_stack.append(t)
    return eval_stack[-1]
                        
# generate an iterator that returns tokens in RPN order

def rpn_iterator(token_iterator):
    operator_stack = []
    for t in token_iterator:
        if is_numerical_token(t):
            try:
                yield int(t)
            except ValueError:
                yield float(t)
        elif is_group_token(t):
            if is_group_open_token(t):
                operator_stack.append(t)
            else:
                while not is_group_open_token(operator_stack[-1]):
                    yield operator_stack.pop()
                operator_stack.pop()
        elif is_operator(t):
            if not operator_stack:
                operator_stack.append(t)
            else:
                while operator_stack and is_operator(operator_stack[-1]):
                    if (compair_associativity(t, operator_stack[-1]) == 0 and compair_precedence(t, operator_stack[-1]) == 0) or compair_precedence(t, operator_stack[-1]) < 0:
                        yield operator_stack.pop()
                    else:
                        break
                operator_stack.append(t)
        else:
            raise ValueError("invalid token " + t)
    while operator_stack:
        yield operator_stack.pop()

# return an iterator of parsed input

def token_iterator(expression):
    tokenization_pattern = re.compile(r"""
        (?:(?:(?<!\d)-)?\d+(?:\.\d+)?) # matches any positive or negative real number
        | (?:\D) # matches any operators
        """, re.VERBOSE)
    return tokenization_pattern.finditer(expression)
        
def calculate(expression):
    try:
        return evaluate_rpn(rpn_iterator(t.group(0) for t in token_iterator(expression)))
    except ValueError as E:
        print(E)
