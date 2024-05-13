## Java Root Locus
This Java code implements a program to visualize the root locus of a given transfer function. The root locus is a graphical representation in the complex plane showing how the poles of a system change as a specific parameter (usually the gain) varies. This information is crucial for control system design and analysis, as it provides insights into stability and performance.
### Components:
The code consists of four main classes:
- **App**: This class serves as the entry point of the program. It creates a `JFrame` (window) and adds a Graph object to display the root locus plot.
- **Graph**: This class is responsible for drawing the root locus plot and animating it. It uses the `RootLocus` class to calculate the roots and other necessary information for plotting.
- **RootLocus**: This class handles the calculations of the root locus. It takes the numerator and denominator coefficients of the transfer function as input and provides methods to:
  - Calculate the roots of the characteristic equation for different gain values.
  - Calculate the poles and zeros of the open-loop transfer function.
  - Calculate the asymptotes of the root locus.
  - Calculate the departure and arrival angles.
- **PolynomialRootFinder**: This class uses the Eigenvalue Decomposition method to find the roots of a polynomial equation, which is essential for calculating the poles and zeros of the transfer function.
### Functionality:
1. **Initialization**:
  - The `App` class creates four `RootLocus` objects with different transfer function coefficients and step sizes.
  - These objects are passed to the `Graph` object, which initializes the plotting parameters and selects the first `RootLocus` object for visualization.
2. **Drawing**:
  - The paintComponent method of the Graph class is responsible for drawing the plot:
    - It draws the x and y axes with appropriate scaling based on the step size.
    - It marks the locations of poles and zeros using different colors and symbols.
    - It draws the asymptotes of the root locus as green lines.
    - It displays the departure and arrival angles near the corresponding poles and zeros.
    - It plots the root locus by iterating through the calculated roots and marking their positions for each gain value.
3. **Animation**:
  - The program animates the root locus by incrementing the index and recalculating the gain value in the `paintComponent` method.
  - When the index reaches a maximum value, it resets and switches to the next `RootLocus` object, displaying a different transfer function's root locus.
### Libraries Used:
- **javax.swing**: Provides graphical user interface components like `JFrame` and `JPanel` for creating the window and drawing the plot.
- **org.ejml**: Offers efficient linear algebra routines for eigenvalue decomposition and complex number calculations.
### Code Breakdown:
1. **App Class**:
  - `main(String args[])`:
This is the entry point of the program.
  - It creates a `JFrame` to hold the graphical output.
  - It initializes four `RootLocus` objects, each representing a different transfer function.
  - It creates a `Graph` object passing the array of `RootLocus` objects.
  - It sets the size of the frame and makes it visible.
2. **Graph Class**:
  - `Graph(RootLocus[] rlArray)`:
    - Constructor that initializes the `rlArray` with the provided array of `RootLocus` objects and sets the current rl to the first element.
    - It calls `updateScale()` to adjust the scaling factor for the plot.
  - `updateScale()`:
    - Calculates the scaling factor based on the step size used in the `RootLocus` calculation. This ensures proper visualization of the plot.
  - `paintComponent(Graphics g)`:
This method is responsible for drawing the root locus plot.
    - It draws the x and y axes with ticks and labels.
    - It draws the zeros and poles of the transfer function.
    - It draws the asymptotes, departure angles, and arrival angles for the root locus.
    - It draws the root locus itself by plotting the roots for different values of the gain 'k'.
    - It animates the plot by incrementing the index and updating the gain 'k'.
    - When the animation reaches the end, it switches to the next transfer function in the `rlArray`.
  - `drawAxis(Graphics g, int width, int height)`:
    - Draws the x and y axes in the center of the panel.
  - `drawAxisTicks(Graphics g, int width, int height)`:
    - Draws ticks on the x and y axes with labels representing the numerical values.
  - `drawRoots(Graphics g, int width, int height, Complex_F64[] poles)`:
    - Draws the poles of the transfer function as blue 'x' marks on the plot.
  - `drawZeros(Graphics g, int width, int height)`:
    - Draws the zeros of the transfer function as red circles on the plot.
  - `drawLocus(Graphics g, int width, int height, Complex_F64[][] roots)`:
    - Draws the root locus by plotting the roots for different values of 'k' as small red dots.
  - `drawAsymptotes(Graphics g, int width, int height)`:
    - Calculates and draws the asymptotes of the root locus as green lines.
  - `drawDepartureAngles(Graphics g, int width, int height)`:
    - Calculates and draws the departure angles from the poles as gray lines with angle values displayed.
  - `drawArrivalAngles(Graphics g, int width, int height)`:
    - Calculates and draws the arrival angles to the zeros as gray lines with angle values displayed.
3. **PolynomialRootFinder Class**:
  - `findRoots(double... coefficients)`:
    - This method takes an array of polynomial coefficients and returns an array of complex roots.
    - It constructs the companion matrix of the polynomial.
    - It uses eigenvalue decomposition to find the roots of the polynomial (which are the eigenvalues of the companion matrix).
4. **RootLocus Class**:
  - `RootLocus(double[] numerator, double[] denominator, double step)`:
    - Constructor that initializes the numerator and denominator coefficients of the transfer function and the step size for gain 'k'.
    - It calculates the zeros and poles of the transfer function.
  - `getStep()`:
    - Returns the step size used for gain 'k'.
  - `calculatRoots()`:
    - Calculates the roots of the closed-loop transfer function for a range of 'k' values.
    - This method is used to generate the data points for plotting the root locus.
  - `calculatePoles(double[] denom)` and `calculateZeros(double[] num)`:
    - These methods use the `PolynomialRootFinder` to find the poles and zeros of the transfer function.
  - `calculateAsymptotesCentroid()`:
    - Calculates the centroid of the asymptotes for the root locus plot.
  - `calculateAsymptotesAngles()`:
    - Calculates the angles of the asymptotes for the root locus plot.
  - `calculateDepartureAngles()`:
    - Calculates the departure angles from the poles.
  - `calculateArrivalAngles()`:
    - Calculates the arrival angles to the zeros.
  - `getZeros(), getPoles(), getAsymptotesCentroid(), getAsymptotesAngles(), getDepartureAngles(), getArrivalAngles(), getRoots()`:
    - These methods provide access to the calculated zeros, poles, asymptote information, and root locus data.

