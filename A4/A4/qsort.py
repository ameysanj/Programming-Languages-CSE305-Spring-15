def qsort(a):
	if a == []: return []
	left = [x for x in a[1:] if x < a[0]]
	right = [x for x in a[1:] if x >= a[0]]
	return qsort(left)+ [a[0]]+ qsort(right)
print(qsort([5,4,6,3,7,9,2,8,1,0])) 
