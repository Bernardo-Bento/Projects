#!/bin/bash
echo "A criar pacientes"
echo -n  "" > pacientes.txt
i=1
while [ $i -lt 11 ]
do
number=$( cat /etc/passwd | grep "^a[0-9]" | head -$i | tail -1 | cut -d ':' -f1 | sed "s/a//g" )
name=$( cat /etc/passwd | grep "^a[0-9]" | head -$i | tail -1 | cut -d ':' -f5 | sed "s/,,,//g")
email=$( cat /etc/passwd | grep "^a[0-9]" | head -$i | tail -1 | cut -d ':' -f1 )

echo "$number;$name;;;$email"@iscte-iul.pt";100 " >> pacientes.txt
i=$(( $i+1 ))
done

echo "Pacientes criados"
