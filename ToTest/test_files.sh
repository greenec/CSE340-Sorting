#!/bin/bash
echo "" > log.txt;
java -cp ../ Tester.ProgramTester -p >> log.txt;
for filename in *; do
    # Take extension available in a filename
    ext=${filename##*\.}
    case "$ext" in
	#copies each .class file one by one into place tester can access it then moves it to done
	class)   mv "$filename" "../IntSortingMethods/" &&
		     #run the tester on the sort
		     #Currently 3 trials, 180000 ms (3 min) per, 1000 ms (1 sec) tolerance, ../GeneratedData as datasource
		     #
		     # Edit the following line to change the parameters when testing the sorts
		     java -Xmx31g -Xms10g -cp ../ Tester.ProgramTester -f "${filename%.*}" -t 3 -l 180000 -c 1000 -d "../GeneratedData" >> log.txt &&
		     # Do not edit other lines
		     #
		     #move sort into finished category
		     mv "../IntSortingMethods/$filename" "../Finished/"
		     ;;
	*)
    esac
done
