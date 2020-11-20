#!/bin/bash
stop=0
while [ $stop -ne 1 ]
do
echo "o que deseja fazer?"
echo "1. Criar Paciente"
echo "2. Criar Medico"
echo "3. Ver stats"
echo "4. Avaliar medico"
echo "0. Sair"

read option 
case $option in
1) ./cria_pacientes.sh
;;
2) echo "Insira o nome do medico: "
read nome
echo "Insira o numero de identificacao do medico: "
read num
echo "Insira a especialidade do medico: "
read esp
echo "Insira o Email do medico: "
read email
./cria_medico.sh "$nome" $num "$esp" "$email"
;;
3) echo "Insira a localidade: "
read localidade 
echo "Insira o saldo minimo: "
read saldo 
./stats.sh $localidade $saldo
;;
4) ./avalia_medicos.sh
;; 
0) echo "adeus, ate a proxima!"
exit 0
$stop=1
;;
*) echo "opcao invalida"
;;
esac
done
