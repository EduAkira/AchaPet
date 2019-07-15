package br.com.achapet.Activity.Pet.listaCard;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import br.com.achapet.R;


public class CardPetViewHolder extends RecyclerView.ViewHolder {

    private CardView cardView;
    private SimpleDraweeView image;
    private TextView raca;
    private TextView porte;
    private TextView sexo;

    public CardPetViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.pet_card_view);
        image = itemView.findViewById(R.id.include_image);
        raca = itemView.findViewById(R.id.pet_card_raca);
        porte = itemView.findViewById(R.id.pet_card_porte);
        sexo = itemView.findViewById(R.id.pet_card_sexo);
    }

    public CardView getCardView() {
        return cardView;
    }

    public SimpleDraweeView getImage() {
        return image;
    }

    public TextView getRaca() {
        return raca;
    }

    public TextView getPorte() {
        return porte;
    }

    public TextView getSexo() {
        return sexo;
    }
}
