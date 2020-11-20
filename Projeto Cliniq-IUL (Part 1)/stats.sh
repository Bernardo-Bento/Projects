#!/bin/bash
localidade=$1
saldo=$2
i=1
j=1
tamanhoMedicos=$( cat medicos.txt | wc -l )
somadorPacientes=0
somadorMedicos=0
while [ $i -le 10 ] 
do
loc=$( cat pacientes.txt | cut -d ';' -f3 | head -$i | tail -1 )

if [ "$localidade" = "$loc" ]
then 
somadorPacientes=$(( $somadorPacientes+1 ))
fi 

i=$(( $i+1 ))
done

while [ $j -le $tamanhoMedicos ]
do
sal=$( cat medicos.txt | cut -d ';' -f7 | head -$j | tail -1 )
if [ $sal -gt $2 ] 
then
somadorMedicos=$(( $somadorMedicos+1 )) 
fi 
j=$(( $j+1 )) 
done
echo "existem $somadorPacientes pacientes em $localidade"
echo "existem $somadorMedicos medicos com saldo superior a $saldo €"
