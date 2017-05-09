#Zadanie 1
set terminal png size 800,600
set out "przebieg_calki.png"
set logscale x
set xtic auto
set ytic auto
set ylabel "Wartosc calki a"
set xlabel "Iteracje"
plot "wariant1aOmega0.6.dat" using 1:2 title 'Omega 0.6' with lines, \
    "wariant1aOmega0.8.dat" using 1:2 title 'Omega 0.8' with lines, \
    "wariant1aOmega1.0.dat" using 1:2 title 'Omega 1.0' with lines, \
    "wariant1aOmega1.5.dat" using 1:2 title 'Omega 1.5' with lines, \
    "wariant1aOmega1.9.dat" using 1:2 title 'Omega 1.9' with lines, \
    "wariant1aOmega1.95.dat" using 1:2 title 'Omega 1.95' with lines, \
    "wariant1aOmega1.99.dat" using 1:2 title 'Omega 1.99' with lines

#Zadanie 2
unset ylabel
unset xlabel
unset logscale
set terminal png size 800,600
set out "density1a.png"
set pm3d
splot "wariant1a_density.dat" with pm3d

set terminal png size 800,600
set out "pot_k_32.png"
set pm3d
splot "wariant1a_pot_k32.dat" with pm3d

set terminal png size 800,600
set out "pot_k_16.png"
set pm3d
splot "wariant1a_pot_k16.dat" with pm3d

set terminal png size 800,600
set out "pot_k_8.png"
set pm3d
splot "wariant1a_pot_k8.dat" with pm3d

set terminal png size 800,600
set out "pot_k_4.png"
set pm3d
splot "wariant1a_pot_k4.dat" with pm3d

set terminal png size 800,600
set out "pot_k_2.png"
set pm3d
splot "wariant1a_pot_k2.dat" with pm3d

set terminal png size 800,600
set out "pot_k_1.png"
set pm3d
splot "wariant1a_pot_k1.dat" with pm3d

set terminal png size 800,600
set out "wariant1a_calka.png"
unset pm3d
set xtic auto
set ytic auto
set ylabel "Wartosc calki a"
set xlabel "Iteracje"
plot "wariant1a_calka.dat" using 1:2 title 'Omega 0.6' with lines