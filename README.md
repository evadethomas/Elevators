PLEASE NOTE:

I added more on Nov 21. I fixed the average, because I incremented passengers when they were created not destroyed.
I also implemented the "linked" requirement with two if statements. I'm sad because I don't want to use another late day.

If these changes don't affect my grade that much, I'd rather not use a late day and go off of my OG submission. Please 
let me know if it's possible to find out the difference with these minimal changes so I can decide to take the grade loss
or keep my late day just in case. If not, no worries. Thank you!!

READ ME:

This repository is for the Elevators assignment for 245. 

Some specific design choices of my implementation:

-This project took me 3 tries. 

-In my final implementation, I decided to have two floorQues (up and down) where when a passenger
hits a "button" in or outside the elevator, the integer of their floor is added to it. 

-Each elevator has a que of passengers that are actually on the elevator.

-For every "tick"/"time unit" the elevator, spawns passengers, unloads and loads them, travels and then re-queues if any
passengers were bumped. I wanted it to be as close as I could to a real elevator.

To see more on OO composition, specific reasoning behind the choices, etc.. read through my extremely detailed comments.

To run, run on elevators3. Feed it a properties file in the first argument if neccessary.