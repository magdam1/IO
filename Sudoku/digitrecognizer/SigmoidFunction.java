package digitrecognizer;
/** Sigmoid function. */
class SigmoidFunction implements Function {
    @Override
    public double apply(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
