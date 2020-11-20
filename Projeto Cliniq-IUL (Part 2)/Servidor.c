#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>

typedef struct {
 int tipo;             // Tipo de Consulta: 1-Normal, 2-COVID19, 3-Urgente
 char descricao[100];  // Descrição da Consulta
 int pid_consulta;     // PID do processo que quer fazer a consulta
} Consulta;

Consulta lista_consultas[10];
int iteradorLista = 1;
int counter1 = 0;
int counter2 = 0;
int counter3 = 0;
int countPerdidas = 0;
int tipoCliente;
int pidCliente;
char descricaoCliente[100];
char infoCliente[200];

void writePidInFile(){
  FILE *fileWrite;
  fileWrite = fopen("SrvConsultas.pid", "w");
  fprintf(fileWrite , "%d\n" , getpid());
  fclose(fileWrite);
}

void checkDisponibilidade(){
  int counter = 0;
  for(int i = 0; i <10 ; i++){
    if (lista_consultas[i].tipo != -1){
      counter++;
   }
   else{
    iteradorLista = i+1;
    //printf("Consulta agendada na sala %d\n", iteradorLista);
    break;
  }
  }
  if(counter == 10){
    printf("Lista de consulas cheia\n");
    tipoCliente = -1;
    countPerdidas++;
    kill(pidCliente, SIGUSR2);
  }
}

void processoFilho(){
int pidFilho;
pidFilho = fork();
if (pidFilho == 0) {
/* Código do filho */
kill(pidCliente, SIGHUP);
iteradorLista--;
printf("Consulta agendada na sala %d\n", iteradorLista);
sleep(10);
kill(pidCliente, SIGTERM);
printf("Consulta terminada na sala %d\n", iteradorLista);
exit(0);
} else {
/* Pai bloqueia */
pidFilho = wait(NULL);
}
}



void signalHandler(int sinal){
  //Reader
  FILE *reader;
  reader = fopen("PedidoConsultas.txt", "r"); 
  fgets(descricaoCliente, 100, reader);
  fscanf(reader, "\n%d\n%d", &pidCliente, &tipoCliente);
  fclose(reader);
  descricaoCliente[strcspn(descricaoCliente, "\n")] = 0;
  printf("Chegou novo pedido de consulta do tipo %d, descricao: %s e de pid %d\n", tipoCliente, descricaoCliente,pidCliente); 
  checkDisponibilidade();
  lista_consultas[iteradorLista].tipo = tipoCliente;
  strcpy(lista_consultas[iteradorLista].descricao,descricaoCliente);
  lista_consultas[iteradorLista].pid_consulta= pidCliente;
  //printf("Consulta agendada na sala %d\n", iteradorLista);
  switch(tipoCliente){
    case 1: counter1++;
    break;
    case 2: counter2++;
    break;
    case 3: counter3++;
    break;
  }
  printf("tipo 1: %d || tipo 2: %d || tipo 3: %d || perdidas: %d\n", counter1, counter2, counter3,countPerdidas);
  processoFilho();
  
}

void updateFile(){
  FILE *writer;
  writer = fopen("StatsConsultas.dat" , "w");
  fprintf(writer, "| tipo 1: %d | tipo 2: %d | tipo 3: %d | perdidas: %d |", counter1, counter2,counter3, countPerdidas);
  fclose(writer);
}

void treatSigint(){
  remove("SrvConsultas.txt");
  printf("\nServidor Fechado com sucesso.\n");
  updateFile();
  exit(0);
}


int main(){
  signal(SIGUSR1, signalHandler);
  signal(SIGINT ,treatSigint);
  printf("Servidor iniciado com sucesso\n");
  char s[100];
  //S1
  for ( int i = 0; i < 10 ; i++){
      lista_consultas[i].tipo = -1;
  }
 
 //s2
  writePidInFile();
  
  //s3
  while(1) {
    fgets(s, 100, stdin);
    s[strlen(s)-1] = '\0';

    if (strcmp(s, "sair") == 0 )
      exit(0); 
  }
  
  
}
