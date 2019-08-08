package pontinisistemas.doctorbrewer.base.ext

import androidx.fragment.app.FragmentActivity


const val TAG_FALHA_IRREVERSIVEL = "FALHA_IRREVERSIVEL"
const val IDENTIFICADOR_AVISO = -1
const val IDENTIFICADOR_ERRO_IRREVERSIVEL = -2
const val TAG_AVISO = "AVISO"

 fun FragmentActivity.showIrreversibleFailure(mensagem: String) {

   /* val dialog = DialogConfirmacao()
    dialog.setTitulo(this.getString(R.string.base_aviso))
    dialog.setMensagem(this.getString(R.string.base_falha_irreversivel) +" " +mensagem)
    dialog.adicionarBotao(getString(R.string.base_ok), IDENTIFICADOR_ERRO_IRREVERSIVEL) { dialog, identificador -> finish() }
    dialog.show(supportFragmentManager, TAG_FALHA_IRREVERSIVEL)*/

}

 fun FragmentActivity.mostrarMensagem(mensagem: String) {
/*

    val dialog = DialogConfirmacao();
    dialog.setTitulo(this.getString(R.string.base_aviso))
    dialog.setMensagem(mensagem);
    dialog.adicionarBotao( this.getString(R.string.base_ok), IDENTIFICADOR_AVISO, ButtonDialog.ButtonDialogListener { dialog, i -> dialog.dismiss() });
    val ft = getSupportFragmentManager().beginTransaction();
    ft.add(dialog, TAG_AVISO);
    ft.commitAllowingStateLoss();
*/

}

/*
 fun FragmentActivity.mostrarMensagem(mensagem: String, listener: ButtonDialog.ButtonDialogListener ) {

    val dialog = DialogConfirmacao();
    dialog.setTitulo(this.getString(R.string.base_aviso))
    dialog.setMensagem(mensagem);
    dialog.adicionarBotao( this.getString(R.string.base_ok), -1, listener);
    val ft = getSupportFragmentManager().beginTransaction();
    ft.add(dialog, TAG_AVISO);
    ft.commitAllowingStateLoss();

    }
    */





