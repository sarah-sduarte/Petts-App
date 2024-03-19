package com.social.android.sarah.petts.fragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


import com.google.firebase.database.DatabaseReference;
import com.social.android.sarah.petts.R;
import com.social.android.sarah.petts.activity.CadastrarPetPerdidoActivity;
import com.social.android.sarah.petts.activity.FiltroActivity;
import com.social.android.sarah.petts.helper.ConfiguracaoFirebase;
import com.social.android.sarah.petts.helper.Permissao;
import com.social.android.sarah.petts.helper.UsuarioFirebase;
import com.social.android.sarah.petts.model.Usuario;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostagemFragment extends Fragment {

    private Button buttonAbrirGaleria, buttonAbrirCamera;
    private Button buttonAcaoCadastrar;
    private static final int SELECAO_CAMERA  = 100;
    private static final int SELECAO_GALERIA = 200;

    private ProgressBar progressBar;
    private CircleImageView imagePerfil;
    private Usuario usuarioLogado;
    private DatabaseReference firebaseRef;
    private DatabaseReference usuariosRef;




    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public PostagemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_postagem, container, false);

        //Configurações iniciais
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        firebaseRef = ConfiguracaoFirebase.getFirebase();
        usuariosRef = firebaseRef.child("usuarios");

        //Validar permissões
        Permissao.validarPermissoes(permissoesNecessarias, getActivity(), 1 );

        //Configurações dos componentes
        inicializarComponentes(view);


        //Inicializar componentes
        buttonAbrirCamera = view.findViewById(R.id.buttonAbrirCamera);
        buttonAbrirGaleria = view.findViewById(R.id.buttonAbrirGaleria);
        buttonAcaoCadastrar = view.findViewById(R.id.buttonAcaoCadastrar);


        //Adiciona evento de clique no botão da camera
        buttonAbrirCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if( i.resolveActivity( getActivity().getPackageManager() ) != null ){
                    startActivityForResult(i, SELECAO_CAMERA );
                }
            }
        });

        //Adiciona evento de clique no botão da galeria
        buttonAbrirGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                if( i.resolveActivity( getActivity().getPackageManager() ) != null ){
                    startActivityForResult(i, SELECAO_GALERIA );
                }
            }
        });

        buttonAcaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CadastrarPetPerdidoActivity.class);
                startActivity(i);
            }
        });




        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == getActivity().RESULT_OK ){

            Bitmap imagem = null;
            Bitmap imagemalerta = null;

            try {

                //Valida tipo de seleção da imagem
                switch ( requestCode ){
                    case SELECAO_CAMERA :
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA :
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), localImagemSelecionada);
                        break;
                }

                //Valida imagem selecionada
                if( imagem == imagem ){

                    //Converte imagem em byte array
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    //Envia imagem escolhida para aplicação de filtro
                    Intent i = new Intent(getActivity(), FiltroActivity.class);
                    i.putExtra("fotoEscolhida", dadosImagem );
                    startActivity( i );

                }





            }catch (Exception e){

                e.printStackTrace();
            }

        }

    }

    private void inicializarComponentes(View view){
        buttonAcaoCadastrar = view.findViewById(R.id.buttonAcaoCadastrar);
    }

}
