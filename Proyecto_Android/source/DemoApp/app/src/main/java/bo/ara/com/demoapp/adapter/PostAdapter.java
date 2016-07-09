package bo.ara.com.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import bo.ara.com.demoapp.R;
import bo.ara.com.demoapp.model.Post;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LENOVO on 09/06/2016.
 */
public class PostAdapter extends ArrayAdapter<Post> {
    public PostAdapter(Context context) {
        super(context, R.layout.post_row_layout);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Recupera objeto con la posicion
        Post post =  getItem(position);

        View currentView;
        //convertview = null -> crear nueva vista
        //otherwise -> usar la vista ya creada
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            currentView = inflater.inflate(R.layout.post_row_layout, parent, false);
        }
        else{
            currentView = convertView;
        }

        //Buscamos referencia nuestros objetos dentro del layout
        CircleImageView imageUser = (CircleImageView) currentView.findViewById(R.id.profile_image_view);
        TextView title = (TextView) currentView.findViewById(R.id.text_title);
        TextView content = (TextView) currentView.findViewById(R.id.text_content);

        //Llena los datos al layout
        //imageUser.setImageResource(R.drawable.user01);
        Glide.with(getContext()).load(post.getUser().getPicture_url()).into(imageUser);
        title.setText(post.getTitle());
        content.setText(post.getContent());

        //Devuelve vista procesada
        return currentView;
    }
}
