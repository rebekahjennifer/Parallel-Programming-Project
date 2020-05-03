#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include<omp.h>

void Sequential_Multiplication(int DIM, double** A, double** B, double** C);
void Parallel_Multiplication(int DIM, double** A, double** B, double** C);

int main(int argc, char *argv[])
{
  int DIM;
  double** A;
  double** B;
  double** C;
  int i=0;
  int j=0;
  printf ("Please, enter the dimensions of matrix multiplication : ");
  scanf("%d", &DIM);
  printf("Matrix Size: %d x %d\n",DIM,DIM);
  
  A =(double **)malloc(DIM*sizeof(double *));
  A[0] = (double *)malloc(DIM*DIM*sizeof(double));
  if(!A)
  {
    printf("memory failed \n");
    exit(1);
  }
  for(i=1; i<DIM; i++)
  {
    A[i] = A[0]+i*DIM;
    if (!A[i])
    {
      printf("memory failed \n");
      exit(1);
    }
  }

  B =(double **)malloc(DIM*sizeof(double *));
  B[0] = (double *)malloc(DIM*DIM*sizeof(double));
  if(!B)
  {
    printf("memory failed \n");
    exit(1);
  }
  for(i=1; i<DIM; i++)
  {
    B[i] = B[0]+i*DIM;
    if (!B[i])
    {
      printf("memory failed \n");
      exit(1);
    }
  }
  
  C =(double **)malloc(DIM*sizeof(double *));
  C[0] = (double *)malloc(DIM*DIM*sizeof(double));
  if(!C)
  {
    printf("memory failed \n");
    exit(1);
  }
  for(i=1; i<DIM; i++)
  {
    C[i] = C[0]+i*DIM;
    if (!C[i])
    {
      printf("memory failed \n");
      exit(1);
    }
  }
  
  for(i=0; i<DIM; i++)
  {
    for(j=0; j<DIM; j++)
    {
	  A[i][j] = rand()%DIM;
	  B[i][j] = rand()%DIM;
    }
  }

Sequential_Multiplication(DIM,A,B,C);
Parallel_Multiplication(DIM,A,B,C);

  free(A[0]);
  free(A);
  free(B[0]);
  free(B);
  free(C[0]);
  free(C);
  return 0;
}

void Sequential_Multiplication(int DIM, double** A, double** B, double** C)
{
  int i=0;
  int j=0;
  int k=0;
  struct timeval t0, t1;
double elapsed_time;
gettimeofday(&t0, 0); 
  for(i = 0; i < DIM; i++){
    for( j = 0; j < DIM; j++){
      for( k = 0; k < DIM; k++){
        C[i][j] +=  A[i][k] * B[k][j];
			}
		}
	}
gettimeofday(&t1, 0);
elapsed_time = (t1.tv_sec-t0.tv_sec) * 1.0f + (t1.tv_usec - t0.tv_usec) / 1000000.0f;
printf("Operation time took for sequential matrix multiplication: %f seconds \n",elapsed_time); 
}

void Parallel_Multiplication(int DIM, double** A, double** B, double** C)
{
int i, j, k;
int t_num, t_id;
int tmp, chunk;

omp_set_num_threads(5);

double start = omp_get_wtime(); 
	#pragma omp parallel private(i,j,k,t_id, tmp) shared(A,B,C,t_num)
	{
		t_id = omp_get_thread_num();
 		if (t_id == 0)
    		{
    			t_num = omp_get_num_threads();
				chunk = DIM/t_num;
    			printf("Starting matrix multiplication with %d threads\n",t_num);
			}
		
		#pragma omp for schedule(static,chunk)
		for (i = 0; i < DIM; i++) 
		{
        		for (j = 0; j < DIM; j++) 
			{
				tmp = 0;
            			for (k = 0; k < DIM; k++) 
				{
					
                			tmp += A[i][k] * B[k][j];
            	}
				C[i][j] = tmp; 
        	}
    	}
	}
double end = omp_get_wtime();
double Op_time = end - start;
printf("Operation time for parallel matrix multiplication %2f seconds \n", Op_time);

}

