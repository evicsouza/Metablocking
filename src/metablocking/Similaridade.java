package metablocking;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.Similarity;

@SuppressWarnings("serial")
public class Similaridade extends Similarity{

	@Override
	public float computeNorm(String field, FieldInvertState state) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float queryNorm(float sumOfSquaredWeights) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float sloppyFreq(int distance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float tf(float freq) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float idf(int docFreq, int numDocs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float coord(int overlap, int maxOverlap) {
		// TODO Auto-generated method stub
		return 0;
	}

}
