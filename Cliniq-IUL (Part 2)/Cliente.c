#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>

typedef struct {
  int tipo;                 // tipo de consulta: 1-Normal , 2-COVID19 , 3-Urgente
  char descricao[100];      // Descrição da consulta 
  int pid_consulta;         // PID do processo que quer fazer a consulta
} Consulta;

Consulta consulta;
int pidServidor;
int sighupReceived = 0;
int canCreateNewConsulta = 1;

int fileExists(){
 FILE *file;
  if(access("PedidoConsulta.txt", F_OK) != -1){
     return 1;
  }
  return 0;
}
void fileRead(){
  FILE *reader;
  char pidNumber[6];
  reader = fopen("SrvConsultas.pid", "r");  
  fgets(pidNumber,6,reader);
  fclose(reader);
  pidServidor = atoi(pidNumber);
}

void writeConsultaInFile(){
  if ( fileExists() == 1 ){
     //perror("\nErro: Uma consulta encontra-se atualmente a decorrer.\n");
     printf("Erro: uma consulta ja se encontra a decorrer\n");
     printf("A tentar novamente daqui a 10 segundos\n");
     remove("PedidoConsulta.txt");
     alarm(10);
   } 
   else{
      FILE *fileWrite;
      fileWrite = fopen("PedidoConsulta.txt", "w");
      consulta.descricao[strcspn(consulta.descricao, "\n")] = 0;
      fprintf(fileWrite , "%s\n%d\n%d \n" ,consulta.descricao, consulta.pid_consulta, consulta.tipo);
      fclose(fileWrite);
      fileRead();
      printf("PID servidor: %d\n", pidServidor);
      kill (pidServidor, SIGUSR1);
      printf("consulta enviada com sucesso\n");
   }
}


void treatSIGUSR2(){
   printf("Consulta não é possivel para o processo %d\n", consulta.pid_consulta);
   remove("PedidoConsulta.txt");
   exit(0);
} 


void treatSighup(){
  printf("Consulta iniciada para o processo %d\n", consulta.pid_consulta);
  sighupReceived = 1;
  //remove("PedidoConsulta.txt");   //Decidi comentar o remove pois, se eu remover o ficheiro quando ele recebe o 
                                    //sinal SIGHUP, nunca vou conseguir verificar se o ficheiro existe ou nao, e assim nao 
                                    //conseguiria completar a alinea c8.
}

void treatSigterm(){
  if (sighupReceived == 1){
   printf("Consulta concluida para o processo %d\n", consulta.pid_consulta);
   remove("PedidoConsulta.txt");
   sighupReceived = 0;
   exit(0);
 }
  else if ( sighupReceived == 0 ){
    printf("\nERROR: Ocorreu um erro com o inicio da consulta\n");
    remove("PedidoConsulta.txt");
    exit(0);
  }
}

void treatSigint(){
  printf("\nO cliente cancelou o pedido!\n");
  remove("PedidoConsulta.txt");
  exit(0);
}
void treatSigalarm(){
  printf("Ficheiro de pedidos de consultas atualizado \n");
  writeConsultaInFile();
  fileRead();
  printf("PID servidor: %d\n", pidServidor);
  
}

int main (){
  
   signal(SIGUSR2, treatSIGUSR2);
   signal(SIGHUP, treatSighup);
   signal(SIGTERM, treatSigterm);
   signal(SIGINT, treatSigint);
   signal(SIGALRM, treatSigalarm);
   char number[20];
   int tipo;
   char descricao[100];
   printf("PID: %d\n", getpid());
   printf("1. Normal\n");
   printf("2. COVID-19\n");   
   printf("3. Urgente\n");
   printf("escolha um tipo de consulta: \n");
   fgets(number , 20 , stdin );
   tipo = atoi(number);
   while(tipo < 1 || tipo > 3 ){
     printf("Tipo de consulta indisponivel\n");
     printf("escolha outro tipo de consulta: \n");
     fgets(number , 20 , stdin );
     tipo = atoi(number);
   }
   printf("adicione uma descricao: \n");
   fgets ( descricao , 20 , stdin );
   
   consulta.tipo = tipo;
   strcpy(consulta.descricao, descricao);
   consulta.descricao[strcspn(consulta.descricao, "\n")] = 0;
   consulta.pid_consulta = getpid();
   char info[100];
   printf("PID consulta: %d\n", getpid());
   printf("Tipo: %d  || descricao: %s ||  PID: %d \n", consulta.tipo,consulta.descricao, consulta.pid_consulta); 
   printf("Ficheiro de pedidos de consultas atualizado \n");
   writeConsultaInFile();
   
   while(1) {
    pause();
  }
   
   
}


