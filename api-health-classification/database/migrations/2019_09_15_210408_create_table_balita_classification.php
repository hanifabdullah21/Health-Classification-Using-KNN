<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTableBalitaClassification extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('balita_classification', function (Blueprint $table) {
          $table->bigIncrements('id');
          $table->unsignedBigInteger('account_id')->nullable();
          $table->unsignedBigInteger('balita_id')->nullable();
          $table->integer('umur');
          $table->date('tanggal_posyandu');
          $table->integer('berat_badan');
          $table->integer('tinggi_badan');
          $table->string('status');
          $table->timestamps();

          $table->foreign('account_id')->references('id')->on('accounts')->onDelete('set null')->onUpdate('cascade');
          $table->foreign('balita_id')->references('id')->on('balita_master')->onDelete('set null')->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('balita_classification');
    }
}
