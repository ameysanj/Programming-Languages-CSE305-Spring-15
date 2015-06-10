
"""

          CSE305 Assignment 4 - Parameter passing and python's List Comprehension
 

          STUDENT NAME- AMEY SANJAY MAHAJAN
          UBIT NAME- ameysanj
          PERSON NUMBER- 50090016
			
"""

"""
	Answer 3a -- qsort1.py

"""


def list_gen1(l):
	answerLeft = []
	answerLeft.append(l)	
	yield answerLeft

def list_gen2(l):
	answerRight = []
	answerRight.append(l) 
	yield answerRight

def qsort(a):
	

	answer = []
	lg = []
	rg = []
	if a == []: return []

	for x in a[1:]:
		if x < a[0]:
			left = x
			lg.append(x)
	for z in qsort(lg):
		answer.append(z)
	answer.append(a[0])
	for y in a[1:]: 
		if y >= a[0]:
			right = y
			rg.append(y)
	for w in qsort(rg):
		answer.append(w)
	return answer
print(qsort([5,4,6,3,7,9,2,8,1,0])) 
