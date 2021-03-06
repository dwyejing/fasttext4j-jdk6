package fasttext;

import fasttext.store.InputStreamFastTextInput;
import fasttext.store.OutputStreamFastTextOutput;

import java.io.IOException;
import java.util.Random;

public class ProductQuantizer {

  private static final int NUM_BITS = 8;
  private static final int KSUB = 1 << NUM_BITS;
  private static final int MAX_POINTS_PER_CLUSTER = 256;
  private static final int MAX_POINTS = MAX_POINTS_PER_CLUSTER * KSUB;
  private static final int SEED = 1234;
  private static final int NUM_ITER = 25;
  private static final double EPS = 1e-7;

  private final int dim;
  private final int nsubq;
  private final int dsub;
  private final int lastdsub;

  private final float[] centroids;
  private final Random rng = new Random(SEED);

  public static int findCentroidsSize(int dimension) {
    return dimension * KSUB;
  }

  public float distL2(float[] x, float[] y, int d) {
    return distL2(x, y, d, 0, 0);
  }

  public float distL2(float[] x, float[] y, int d, int xpos, int ypos) {
    float dist = 0.0f;
    for (int i = 0; i < d; i++) {
      float tmp = x[i + xpos] - y[i + ypos];
      dist += tmp * tmp;
    }
    return dist;
  }

  public ProductQuantizer(int dim, int dsub) {
    this.dim = dim;
    this.dsub = dsub;
    this.centroids = new float[dim * KSUB];
    int nsubq = dim / dsub;
    int lastdsub = dim % dsub;
    if (lastdsub == 0) {
      this.lastdsub = dsub;
      this.nsubq = nsubq;
    } else {
      this.lastdsub = lastdsub;
      this.nsubq = nsubq + 1;
    }
  }

  public ProductQuantizer(int dim, int nsubq, int dsub, int lastdsub, float[] centroids) {
    this.dim = dim;
    this.nsubq = nsubq;
    this.dsub = dsub;
    this.lastdsub = lastdsub;
    this.centroids = centroids;
  }

  public int dim() {
    return this.dim;
  }

  public int dsub() {
    return this.dsub;
  }

  public int nsubq() {
    return this.nsubq;
  }

  public int lastdsub() {
    return this.lastdsub;
  }

  public float[] centroids() {
    return this.centroids;
  }

  public float getCentroid(int position) {
    return centroids[position];
  }

  public int getCentroidsPosition(int m, int i) {
    if (m == nsubq - 1) {
      return m * KSUB * dsub + i * lastdsub;
    } else {
      return (m * KSUB + i) * dsub;
    }
  }

  private float assignCentroid(float[] x, int xStartPosition, int c0Position, QCodes codes, int codeStartPosition, int d) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  private void eStep(float[] x, int cPosition, QCodes codes, int d, int n) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  private void mStep(float[] x0, int cPosition, QCodes codes, int d, int n) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  private void kmeans(float[] x, int cPosition, int n, int d) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public void train(int n, float[] x) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public void computeCode(float[] x, QCodes codes, int xBeginPosition, int codeBeginPosition) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public void computeCodes(float[] x, QCodes codes, int m) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public float mulCode(Vector x, QCodes codes, int t, float alpha) {
    float res = 0.0f;
    int d = dsub;
    int codePos = nsubq + t;
    for (int m = 0; m < nsubq; m++) {
      int c = getCentroidsPosition(m, codes.get(m + codePos));
      if (m == nsubq - 1) {
        d = lastdsub;
      }
      for(int n = 0; n < d; n++) {
        res += x.data[m * dsub + n] * centroids[c * n];
      }
    }
    return res * alpha;
  }

  public void addCode(Vector x, QCodes codes, int t, float alpha) {
    int d = dsub;
    int codePos = nsubq * t;
    for (int m = 0; m < nsubq; m++) {
      int c = getCentroidsPosition(m, codes.get(m + codePos));
      if (m == nsubq - 1) {
        d = lastdsub;
      }
      for(int n = 0; n < d; n++) {
        x.data[m * dsub + n] += alpha * centroids[c + n];
      }
    }
  }

  public void save(OutputStreamFastTextOutput os) throws IOException {
    os.writeInt(dim);
    os.writeInt(nsubq);
    os.writeInt(dsub);
    os.writeInt(lastdsub);
    for (int i = 0; i < centroids.length; i++) {
      os.writeFloat(centroids[i]);
    }
  }

  public static ProductQuantizer load(InputStreamFastTextInput is) throws IOException {
    int dim = is.readInt();
    int nsubq = is.readInt();
    int dsub = is.readInt();
    int lastdsub = is.readInt();
    float[] centroids = new float[dim * KSUB];
    for (int i = 0; i < centroids.length; i++) {
      centroids[i] = is.readFloat();
    }
    return new ProductQuantizer(dim, nsubq, dsub, lastdsub, centroids);
  }

}
