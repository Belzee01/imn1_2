set terminal png size 800,600
set out "zadanie1a.png"
set pm3d
splot "wariant1a.dat" with pm3d

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