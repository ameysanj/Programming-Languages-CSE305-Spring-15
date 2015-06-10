
/*
 *          CSE305 Assignment 4 - Parameter passing and python's List Comprehension
 * 
 *          STUDENT NAME- AMEY SANJAY MAHAJAN
 *          UBIT NAME- ameysanj
 *          PERSON NUMBER- 50090016
 */


/*
 *Answer 1 a - 
 *In the earlier normalize, there was a call by value, meaning that the value was passed as a parameter to the function.
 *The effect of this was that the changes made to that parameter were only
 *handled "locally" and when the program returned to main(), it did not change the parameter passed.
 *When the pointer was used instead in normalize(), and the address of r was passed, the changes were performed on that r and were returned to main(). 
 *
 */


/*
 *Answer 1 b - 
 * 
 */ 
#include <stdio.h>

typedef struct {
                 int numr;
                 int denr;
                 } 
	RATIONAL;
int main() {
RATIONAL r;
	int normalize(RATIONAL *r) {
   		int gcd(int x, int y) {
        	while (x != y)
            	if (x > y)
                	x = x-y;
                else y=y-x;
			return x; 
		}
        int g = gcd(r->numr, r->denr);
        r->numr = r->numr/g;
        r->denr = r->denr/g;
	}
    r.numr = 77;
    r.denr = 88;
    normalize(&r);
    printf("%d/%d\n", r.numr, r.denr);
    }
