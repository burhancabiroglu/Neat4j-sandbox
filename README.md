# Neuroevolution Of Augmenting Topologies

## Abtract

NeuroEvolution of Augmenting Topologies (NEAT) is a genetic algorithm (GA) for the generation of evolving artificial neural networks (a neuroevolution technique) developed by Ken Stanley in 2002 while at The University of Texas at Austin. It alters both the weighting parameters and structures of networks, attempting to find a balance between the fitness of evolved solutions and their diversity. It is based on applying three key techniques: tracking genes with history markers to allow crossover among topologies, applying speciation (the evolution of species) to preserve innovations, and developing topologies incrementally from simple initial structures. <a href="https://en.wikipedia.org/wiki/Neuroevolution_of_augmenting_topologies">wikipedia</a>
</br>
</br>

## AI Mario From Stratch
Built using libgdx.<br>
<b>Fitness function</b> = <code><strong>ƒ(+=$correctJump*5)</strong></code>


```java
// Loss ƒunction: categorical_crossentropy

NetworkSkeleton skeleton = new NetworkSkeleton();
skeleton.add(new LayerSkeleton(1,3));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(3,8));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(8,4));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(4,3));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(3,2));

neatPlayer = new NEAT(skeleton,10,0.1,100);
```

<div align-items=center; style="text-align: center;" width="100%">
<img src ="assetmd/img.gif" width="80%" >
</div>
</br>
</br>

## Scope Of The Project

In this project, it is aimed to realize a high-performance neat algorithm that can solve given problems by using advanced object-oriented programming.
</br>
</br>

## Implementation Note

The game uses the `Neat4j` library to evolve weights and biases for a fixed neural-network skeleton. It does not yet use structural NEAT features such as innovation numbers, speciation, or node/connection topology mutation.
</br>
</br>

## Developer Guide

### <ul>
### <li> Build Own Neat Network 
Build your first neat mesh and solve problems easily

```java
// * Problem: XOR GATES PROBLEM
Matrix inputData = new Matrix(new double[][]{
            { 0, 0 },
            { 0, 1 },
            { 1, 0 },
            { 1, 1 },
});

Matrix outputData = new Matrix(new double[][]{
            { 0 },
            { 1 },
            { 1 },
            { 0 },
});


```
```java
// declare lightweight architecture
NetworkSkeleton skeleton = new NetworkSkeleton();

skeleton.add(new LayerSkeleton(2,4));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(4,4));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(4,3));
skeleton.add(new LayerSkeleton(ActivationAnnotation.ReLU));
skeleton.add(new LayerSkeleton(3,1));



/* 
NEAT(skeleton,populationSize,mutationRate,generation)
* skeleton: NetworkSkeleton
* populationSize: Int
* mutationRate: Double | Float
* generation: Int
*/

NEAT neat = new NEAT(skeleton,60,0.1,500);

neat.fit(inputData,outputData);

NeatNetwork bestNetwork = neat.best();

// Test own network
bestNetwork.forward(inputData).log();

```
```bash
> Task :Main.main()
{0,0} => 0.004609976235675384 
{0,1} => 0.9999217817261652 
{1,0} => 1.0091362689410868 
{1,1} => 0.035621171754300907

```


</li>

</ul>
