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

int fileExists(){
 FILE *file;
  if(access("StatsConsultas.dat", F_OK) != -1){
     return 1;
  }
  return 0;
}

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
  reader = fopen("PedidoConsulta.txt", "r"); 
  fgets(descricaoCliente, 100, reader);
  fscanf(reader, "\n%d\n%d", &pidCliente, &tipoCliente);
  fclose(reader);
  descricaoCliente[strcspn(descricaoCliente, "\n")] = 0;
  printf("Chegou novo pedido de consulta do tipo %d, descricao: %s e de pid %d\n", tipoCliente, descricaoCliente,pidCliente); 
  checkDisponibilidade();
  lista_consultas[iteradorLista].tipo = tipoCliente;
  strcpy(lista_consultas[iteradorLista].descricao,descricaoCliente);
  lista_consultas[iteradorLista].pid_consulta= pidCliente;
  switch(tipoCliente){
    case 1: counter1++;
    break;
    case 2: counter2++;
    break;
    case 3: counter3++;
    break;
  }
  printf("Stats deste processo: \nPerdidas: %d || tipo 1: %d || tipo 2: %d || tipo 3: %d\n", countPerdidas, counter1, counter2, counter3);
  processoFilho();
  
}

void updateFile(){
  int contadores[4];
  int aux[4];
  FILE *writer;
  FILE *reader;
  reader = fopen("StatsConsultas.dat","r");
  fread(aux, sizeof(aux),1,reader);
  fclose(reader);
  contadores[0] = aux[0] + countPerdidas;
  contadores[1] = aux[1] + counter1;
  contadores[2] = aux [2] + counter2;
  contadores[3] = aux [3] + counter3;
  
  writer = fopen("StatsConsultas.dat" , "w");
  fwrite(contadores, sizeof(contadores),1,writer);
  fclose(writer);
  printf("Stats Atualizadas:\n");
  for (int i = 0; i < 4; i++){
      printf("%d\n", contadores[i]);
    }
 
}

void treatSigint(){
  remove("SrvConsultas.pid");
  printf("\nServidor Fechado com sucesso.\n");
  if( fileExists() == 1 ){
  updateFile();
  }
  if (fileExists() == 0){
    int contadores[4];
    contadores[0] = countPerdidas;
    contadores[1] = counter1;
    contadores[2] = counter2;
    contadores[3] = counter3;
  
    FILE *writer;
    writer = fopen("StatsConsultas.dat" , "w");
    fwrite(contadores, sizeof(contadores),1,writer);
    fclose(writer);
    for (int i = 0; i < 4; i++){
      printf("%d\n", contadores[i]);
    }
  }
  exit(0);
}


int main(){
  signal(SIGUSR1, signalHandler);
  signal(SIGINT ,treatSigint);
  printf("Servidor iniciado com sucesso\n");
 
  //S1
  for ( int i = 0; i < 10 ; i++){
      lista_consultas[i].tipo = -1;
  }
 
 //s2
  writePidInFile();
  
  //s3
  while(1) {
    pause();
  }
  
  
}
