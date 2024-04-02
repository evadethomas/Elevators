READ ME:

This repository is for the Elevators assignment for 245. 

Some specific design choices of my implementation:

-In my final implementation, I decided to have two floorQues (up and down) where when a passenger
hits a "button" in or outside the elevator, the integer of their floor is added to it. 

-Each elevator has a que of passengers that are actually on the elevator.

-For every "tick"/"time unit" the elevator, spawns passengers, unloads and loads them, travels and then re-queues if any
passengers were bumped. I wanted it to be as close as I could to a real elevator.

To see more on OO composition, specific reasoning behind the choices, etc.. read through my extremely detailed comments.

To run, run on elevators3. Feed it a properties file in the first argument if neccessary.
