#Zadanie 1
set terminal png size 800,600
set out "calka_globalnie.png"
set xtic auto
set ytic auto
set logscale
set ylabel "Wartosc calki a"
set xlabel "Iteracje"
plot "warB_integral_glob_omega0.1.dat" using 1:2 title 'Relaksacja globalna : Omega 0.1' with lines, \
"warB_integral_glob_omega0.2.dat" using 1:2 title 'Relaksacja globalna : Omega 0.2' with lines, \
"warB_integral_glob_omega0.3.dat" using 1:2 title 'Relaksacja globalna : Omega 0.3' with lines, \
"warB_integral_glob_omega0.4.dat" using 1:2 title 'Relaksacja globalna : Omega 0.4' with lines, \
"warB_integral_glob_omega0.5.dat" using 1:2 title 'Relaksacja globalna : Omega 0.5' with lines, \
"warB_integral_glob_omega0.6.dat" using 1:2 title 'Relaksacja globalna : Omega 0.6' with lines, \
"warB_integral_glob_omega0.7.dat" using 1:2 title 'Relaksacja globalna : Omega 0.7' with lines, \
"warB_integral_glob_omega0.8.dat" using 1:2 title 'Relaksacja globalna : Omega 0.8' with lines, \
"warB_integral_glob_omega0.9.dat" using 1:2 title 'Relaksacja globalna : Omega 0.9' with lines

set terminal png size 800,600
set out "calka_lokalnie.png"
set xtic auto
set ytic auto
unset logscale
set ylabel "Wartosc calki a"
set xlabel "Iteracje"
plot "warB_integral_lok_omega1.1.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.1' with lines, \
"warB_integral_lok_omega1.2.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.2' with lines, \
"warB_integral_lok_omega1.3.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.3' with lines, \
"warB_integral_lok_omega1.4.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.4' with lines, \
"warB_integral_lok_omega1.5.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.5' with lines, \
"warB_integral_lok_omega1.6.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.6' with lines, \
"warB_integral_lok_omega1.7.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.7' with lines, \
"warB_integral_lok_omega1.8.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.8' with lines, \
"warB_integral_lok_omega1.9.dat" using 1:2 title 'Relaksacja lokalna : Omega 1.9' with lines

#Potencjaly
unset ylabel
unset xlabel
set terminal png size 4000,8000
set out "potential_density.png"
set pm3d
set multiplot layout 10,2 rowsfirst
    splot "warB_pot_omega0.1.dat" with pm3d title 'Relaksacja globalna 0.1'
    splot "warB_pot_omega0.2.dat" with pm3d title 'Relaksacja globalna 0.2'
    splot "warB_pot_omega0.3.dat" with pm3d title 'Relaksacja globalna 0.3'
    splot "warB_pot_omega0.4.dat" with pm3d title 'Relaksacja globalna 0.4'
    splot "warB_pot_omega0.5.dat" with pm3d title 'Relaksacja globalna 0.5'
    splot "warB_pot_omega0.6.dat" with pm3d title 'Relaksacja globalna 0.6'
    splot "warB_pot_omega0.7.dat" with pm3d title 'Relaksacja globalna 0.7'
    splot "warB_pot_omega0.8.dat" with pm3d title 'Relaksacja globalna 0.8'
    splot "warB_pot_omega0.9.dat" with pm3d title 'Relaksacja globalna 0.9'
    splot "warB_pot_omega1.1.dat" with pm3d title 'Relaksacja lokalna 1.1'
    splot "warB_pot_omega1.2.dat" with pm3d title 'Relaksacja lokalna 1.2'
    splot "warB_pot_omega1.3.dat" with pm3d title 'Relaksacja lokalna 1.3'
    splot "warB_pot_omega1.4.dat" with pm3d title 'Relaksacja lokalna 1.4'
    splot "warB_pot_omega1.5.dat" with pm3d title 'Relaksacja lokalna 1.5'
    splot "warB_pot_omega1.6.dat" with pm3d title 'Relaksacja lokalna 1.6'
    splot "warB_pot_omega1.7.dat" with pm3d title 'Relaksacja lokalna 1.7'
    splot "warB_pot_omega1.8.dat" with pm3d title 'Relaksacja lokalna 1.8'
    splot "warB_pot_omega1.9.dat" with pm3d title 'Relaksacja lokalna 1.9'
    splot "warB_density.dat" with pm3d title 'Gestosc'
    splot "warB_density.dat" with pm3d title 'Gestosc'
unset multiplot

set terminal png size 800,600
set out "pot_k_16.png"
set pm3d
splot "warB_mw_k16.dat" with pm3d

set terminal png size 800,600
set out "pot_k_8.png"
set pm3d
splot "warB_mw_k8.dat" with pm3d

set terminal png size 800,600
set out "pot_k_4.png"
set pm3d
splot "warB_mw_k4.dat" with pm3d

set terminal png size 800,600
set out "pot_k_2.png"
set pm3d
splot "warB_mw_k2.dat" with pm3d

set terminal png size 800,600
set out "pot_k_1.png"
set pm3d
splot "warB_mw_k1.dat" with pm3d