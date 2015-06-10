/*
 *          CSE305 Assignment 4 - Parameter passing and python's List Comprehension
 * 
 *          STUDENT NAME- AMEY SANJAY MAHAJAN 
 *          UBIT NAME- ameysanj
 *          PERSON NUMBER- 50090016 
 */

/**
 *
 *Answer 2a -
 *
 *
 */

#include <stdio.h>



int sigma(int *k, int low, int high, int expr()) {
 int sum = 0;
 for (*k=low; *k<=high; (*k)++) {
 sum = sum + expr();
 }
 return sum;
}


int main(){

	int i;
	int j;
	int k;
	
	int thunk_k(){
		return k;
	}
	int thunk_j(){
		return j*sigma(&k,0,4,thunk_k);
	}
	int thunk_i(){
		return i*sigma(&j,0,4,thunk_j);
	}
	printf("%d\n", sigma(&i,0,4,thunk_i));	
}
