<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTableBumilTraining extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('bumil_training', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('nama');
            $table->integer('usia_bumil');
            $table->integer('usia_kehamilan');
            $table->double('berat_badan');
            $table->double('tinggi_badan');
            $table->string('status');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('bumil_training');
    }
}
