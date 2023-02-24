import { Item } from './item';

export interface RecipeItem extends Item {
  itemCount: number;
}

export interface Recipe {
  id: string;
  name: string;
  imageUrl: string;
  difficulty: string;
  prepTime: number;
  cookTime: number;
  servingSize: number;
  calories: number;
  protein: number;
  carbohydrates: number;
  sugar: number;
  fibre: number;
  cholestrol: number;
  sodium: number;
  instructions: string;
  ingredients: RecipeItem[];
}
