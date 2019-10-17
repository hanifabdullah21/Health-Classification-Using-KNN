<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTableBumilClassification extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('bumil_classification', function (Blueprint $table) {
          $table->bigIncrements('id');
          $table->unsignedBigInteger('account_id')->nullable();
          $table->unsignedBigInteger('dusun_id')->nullable();
          $table->string('nama');
          $table->integer('usia_bumil');
          $table->integer('usia_kehamilan');
          $table->double('berat_badan');
          $table->double('tinggi_badan');
          $table->double('LILA');
          $table->boolean('KEK');
          $table->date('tanggal_posyandu');
          $table->string('status');
          $table->timestamps();

          $table->foreign('account_id')->references('id')->on('accounts')->onDelete('set null')->onUpdate('cascade');
          $table->foreign('dusun_id')->references('id')->on('dusun')->onDelete('set null')->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('bumil_classification');
    }
}
