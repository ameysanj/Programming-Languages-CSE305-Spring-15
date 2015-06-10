

"""

          CSE305 Assignment 4 - Parameter passing and python's List Comprehension
 

          STUDENT NAME- AMEY SANJAY MAHAJAN
          UBIT NAME- ameysanj
          PERSON NUMBER- 50090016 
			
"""

"""
	Answer 3b -- qsort2.py

"""

def list_gen1(l,thk):
	answerLeft = []
	for x in l[1:]:
		if x < l[0]:
			left = x
			answerLeft.append(left)	
	thk(answerLeft)

def list_gen2(l,thk):
	answerRight = []
	for y in l[1:]: 
		if y >= l[0]:
			right = y
			answerRight.append(right) 
	thk(answerRight)
	
def qsort(a):
	answer = []
	if a == []: return []
	def thunk1(x):
		answer.extend(qsort(x))
	list_gen1(a,thunk1)
	answer.append(a[0])
	def thunk2(x):
		answer.extend(qsort(x))
	list_gen2(a,thunk2)
	return answer

print(qsort([5,4,6,3,7,9,2,8,1,0])) 
