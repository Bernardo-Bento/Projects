#!/bin/bash
i=1;
l=$( cat medicos.txt | wc -l ) 
numero=$2
mail=$4
num=0
while [ $i -le $l ]
do
num=$( cat medicos.txt | cut -d ';' -f2 | head -$i | tail -1 )
email=$( cat medicos.txt | cut -d ';' -f4 | head -$i | tail -1 )
if [ $numero -eq $num -o "$mail" = "$email" ]
then 
echo "O numero de cedula profissioal ou o email do medico inserido ja existe! tente novamente"
exit 0
fi
i=$(( $i+1 )) 
done
echo "A criar medico"
echo "$1;$2;$3;$4;0;0;0" >> medicos.txt 
echo "medico criado"
