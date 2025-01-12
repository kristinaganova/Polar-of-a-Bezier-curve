# Interactive Bézier Curve with Polars

This project is an interactive Java application created as part of the **CAGD** course at **Sofia University**. It demonstrates the use of Bézier curves and their polar transformations, allowing users to explore and visualize these concepts interactively. The program supports adding control points, adjusting parameters, and exploring recursive polar levels.

## Features

- **Interactive Control Points**: Add control points by clicking on the canvas.
- **Bezier Curve Visualization**: Dynamically render Bézier curves based on the control points.
- **Polar Transformations**: Visualize intermediate curves (polars) created by recursive linear interpolation of control points.
- **Adjustable Parameters**:
  - `t1`: Controls the interpolation ratio for polar transformations.
  - `polarLevel`: Sets the number of recursive polar transformations applied.
- **Keyboard Controls**:
  - **UP/DOWN**: Increase or decrease the `t1` parameter.
  - **LEFT/RIGHT**: Increase or decrease the polar level.
  - **C**: Clear all control points.
- **Smooth Rendering**: Anti-aliasing ensures high-quality visuals.
- **Dynamic Coloring**: Each polar level is displayed with a distinct color for clarity.

## Academic Context

This project is designed to provide a practical understanding of Bézier curves and their mathematical properties, including:
1. Curve construction using Bernstein polynomials.
2. Recursive polar transformations and their visualization.
3. Application of linear interpolation for control point transformations.

The project is part of the coursework for the **CAGD** course at Sofia University, aiming to combine theory and practice in computer graphics.
