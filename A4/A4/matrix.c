
/*
 *          CSE305 Assignment 4 - Parameter passing and python's List Comprehension
 * 
 *          STUDENT NAME- AMEY SANJAY MAHAJAN
 *          UBIT NAME- ameysanj
 *          PERSON NUMBER- 50090016 
 */


/**
 *
 *Answer 2b - 
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

void matmult(int a[2][2], int b[2][2], int c[2][2]){
	int i;
	int j;	
	int k;
	int thunk(){
		return a[i][k] * b[k][j];	
	}
	for(i = 0;i<2;i++){	
		for(j = 0;j<2;j++){
			c[i][j] = sigma(&k,0,1,thunk);	
		}	
	}
} 


int main(){
	int a[2][2] = {{1,2}, {3,4}};
 	int b[2][2] = {{5,6}, {7,8}};
 	int c[2][2];
 	matmult(a, b, c);
 	printf("%d %d\n%d %d\n",
			c[0][0], c[0][1], c[1][0], c[1][1]);
}
