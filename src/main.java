import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.search.Query;

import DataStructures.EntityProfile;
import info.debatty.java.stringsimilarity.Jaccard;

public class main {


	public static double computeSimilarity(Set<String> TokensSource, Set<String> TokensTarget) {
		Set<String> intersection = new HashSet<String>(TokensSource);
		intersection.retainAll(TokensTarget);

		return ((double)intersection.size())/Math.min(TokensSource.size(), TokensTarget.size());
	}

	public static void main(String[] args) {
		ArrayList<EntityProfile> EntityListSource = null;
		ArrayList<EntityProfile> EntityListTarget = null;

		String INPUT_PATH1 = "inputs/dataset1_abt";
		String INPUT_PATH2 = "inputs/dataset2_buy";
		Jaccard jaccard = new Jaccard();


		// reading the files
		ObjectInputStream ois1;
		ObjectInputStream ois2;
		try {
			ois1 = new ObjectInputStream(new FileInputStream(INPUT_PATH1));
			ois2 = new ObjectInputStream(new FileInputStream(INPUT_PATH2));
			EntityListSource = (ArrayList<EntityProfile>) ois1.readObject();
			EntityListTarget = (ArrayList<EntityProfile>) ois2.readObject();

			ois1.close();
			ois2.close();

			//token blocking
			Map<String, Set<EntityProfile>> tokenBlocking = new HashMap<String, Set<EntityProfile>>();
			Map<String, Set<EntityProfile>> metablocking = new HashMap<String, Set<EntityProfile>>();


			for (EntityProfile entitySource : EntityListSource) {
				for (String token : entitySource.generateTokens()) {
					if (tokenBlocking.containsKey(token)) {
						tokenBlocking.get(token).add(entitySource);
					} else {
						Set<EntityProfile> set =  new HashSet<EntityProfile>();
						set.add(entitySource);
						tokenBlocking.put(token, set);
					}
				}
			}

			for (EntityProfile entityTerget : EntityListTarget) {
				for (String token : entityTerget.generateTokens()) {
					if (tokenBlocking.containsKey(token)) {
						tokenBlocking.get(token).add(entityTerget);
					} else {
						Set<EntityProfile> set =  new HashSet<EntityProfile>();
						set.add(entityTerget);
						tokenBlocking.put(token, set);
					}
				}
			}


			//ver o token blocks
			//			for (String token : tokenBlocking.keySet()) {
			//				System.out.println(token + ": " + tokenBlocking.get(token));
			//			}

			//metablocking (computar similaridade)
			for (String token : tokenBlocking.keySet()) {
				if (tokenBlocking.get(token).size() > 1) {
					for (EntityProfile entity : tokenBlocking.get(token)) {
						//COMPARAR AS ENTIDADES DO BLOCO QUE SEJAM DE BASES DIFERENTES
						//EU VOU COLOCAR OS PARES DE ENTIDEDES E SUAS SIMILARIDADES EM UMA EST. DE DADOS
					}
				}
			}

			//Poda 
			//Vão remover todos os pares de entidades com similaridade < 0.2
			for (String token : tokenBlocking.keySet()) {
				for (EntityProfile entity : tokenBlocking.get(token))
					if(jaccard.similarity(token, entity.toString()) > 0.2) {
						Set<EntityProfile> set =  new HashSet<EntityProfile>();
						set.add(entity);
						metablocking.put(token, set);

					}
			}
//			System.out.println(tokenBlocking.size());
//			System.out.println(metablocking.size());

//			for (String token : tokenBlocking.keySet()) {
//				System.out.println(token + ": " + tokenBlocking.get(token));
//			}
			for (String token : metablocking.keySet()) {
				System.out.println(token + ": " + tokenBlocking.get(token));
			}



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void tokenblocking() {

	}


}
