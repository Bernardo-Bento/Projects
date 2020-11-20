#!/bin/bash
i=1
tamanho=$( cat medicos.txt | wc -l )
echo -n "" > lista_negra_medicos.txt 
while [ $i -le $tamanho ]
do 
numConsultas=$( cat medicos.txt | cut -d ';' -f6 | head -$i | tail -1 )
rating=$( cat medicos.txt | cut -d ';' -f5 | head -$i | tail -1 )
line=$( cat medicos.txt | head -$i | tail -1 )
if [ $numConsultas -gt 6 -a $rating -lt 5 ]
then 
echo $line >> lista_negra_medicos.txt
fi 
i=$(( $i+1 ))
done 
cat lista_negra_medicos.txt
