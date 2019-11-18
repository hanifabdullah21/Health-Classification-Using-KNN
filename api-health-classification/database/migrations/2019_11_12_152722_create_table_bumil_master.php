<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTableBumilMaster extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('bumil_master', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->unsignedBigInteger('account_id')->nullable();
            $table->unsignedBigInteger('dusun_id')->nullable();
            $table->string('nama');
            $table->date('tanggal_lahir');
            $table->date('tanggal_kehamilan');
            $table->timestamps();

            $table->foreign('account_id')->references('id')->on('accounts')->onDelete('set null')->onUpdate('cascade');
            $table->foreign('dusun_id')->references('id')->on('dusun')->onDelete('set null')->onUpdate('cascade');
        });

        Schema::table('bumil_classification', function (Blueprint $table) {
            $table->unsignedBigInteger('bumil_id')->nullable();

            $table->foreign('bumil_id')->references('id')->on('bumil_master')->onDelete('cascade')->onUpdate('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('bumil_classification', function (Blueprint $table) {
            $table->dropForeign(['bumil_id']);
            $table->dropColumn(['bumil_id']);
        });
        Schema::dropIfExists('bumil_master');
    }
}
