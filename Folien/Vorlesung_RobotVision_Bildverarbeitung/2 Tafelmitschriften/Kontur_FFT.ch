#include <stdio.h>



int main() {

   // ----- Konturbeschreibung -------
   const int   M         = 4; 					// Anzahl der Segmente
   double	  Dl[]       = {100,100, 100, 100};		          // L‰ngen der Segmente
    //double	  Dl[]       = {103,100, 103, 100};	
   
   double	  DPsi[]    = {-90, -90, -90, -90}; 		// Winkel in Grd.
   const int  P	      = 30;					// Anzahl der zu berechnenden Deskriptoren
   // -----------------------------------
   
   int       i,  j, n, lGes=0;
   double a[P], b[P], A[P], ld[M];
   const double PI=3.1415926;
 
   // Gesamtl‰nge ausrechnen und Winkel in Bogenmaﬂ umrechnen   
   for(j=0; j<M; j++){ 		// ¸ber alle Segmente
	   lGes    = lGes + Dl[j];      	// Gesamtl‰nge der Kontur berechnen
	   DPsi[j] = DPsi[j] /180*PI;   // Delta Psi in Bogenmass
   }
   
   // L‰ngennormierung der Segmente
   for(j=0; j<M; j++){ 		// f¸r alle Segmente
	   Dl[j] = Dl[j]/lGes *2*PI;		
   }
  
   // Berechnung der ld
   for(j=0; j<M; j++){ 		// f¸r alle Segmente	
	   ld[j] = 0;				// Init.
	   for(i=0; i<=j; i++){
		ld[j] = ld[j] + Dl[i];  	// Normierte Konturl‰nge berechnen
	   }
   }
  
   // Berechnung der Deskriptoren
    for(n=1; n<P; n++){            // alle Deskriptoren
	a[n] =0;
	b[n] =0;		    
	for(j=0; j<M; j++){		// alle Segmente
		a[n] =a[n]+DPsi[j] * sin(n*ld[j]);
		b[n] =b[n]+DPsi[j] * cos(n*ld[j]);			
	}
	a[n] = -a[n]/(n*PI);
	b[n] = +b[n]/(n*PI);
	A[n] = sqrt(a[n]*a[n] + b[n]*b[n]);
   }  
   
   // Ergebnisse drucken

   //
   for(n=0;  n<M;  n++){
         printf("%10.2f \n",   ld[n]);
   }
   //
   printf("\n\n\n");
   printf("Deskriptoren \n"); 
   printf("  n      a            b             A\n");
   for(n=1;  n<P;  n++){
         printf("%3d:   %10.6f   %10.6f    %10.6f\n",   n,   a[n], b[n], A[n]);
   }

   getchar();   
   getchar();
   return 0; 

}



