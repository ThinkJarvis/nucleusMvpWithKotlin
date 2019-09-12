package com.qinggan.myapplication.nucleusMvp


/**
 * This is the singleton where all presenters are stored.
 */
enum class PresenterStorage {
    INSTANCE;

    var idToPresenter: HashMap<String, Presenter<*>?> = HashMap()
    var presenterToId: HashMap<Presenter<*>?, String> = HashMap()

    /**
     * Adds a presenter to the storage
     *
     * @param presenter a presenter to add
     */
    fun add(presenter: Presenter<*>?) {
        val id = presenter?.javaClass?.simpleName ?: "" + "/" +
        System.nanoTime() + "/" +
        (Math.random() * Integer.MAX_VALUE).toInt()
        idToPresenter[id] = presenter
        presenterToId[presenter] = id
        presenter?.addOnDestroyListener(object : Presenter.OnDestroyListener {
            override fun onDestroy() {
                idToPresenter.remove(presenterToId.remove(presenter))
            }
        })
    }

    /**
     * Returns a presenter by id or null if such presenter does not exist.
     *
     * @param id  id of a presenter that has been received by calling {@link #getId(Presenter)}
     * @param <P> a type of presenter
     * @return a presenter or null
     */
    fun <P> getPresenter(id: String?): P? {
        //noinspection unchecked
        return idToPresenter.get(id) as P?
    }

    /**
     * Returns id of a given presenter.
     *
     * @param presenter a presenter to get id for.
     * @return if of the presenter.
     */
    fun getId(presenter: Presenter<*>?): String? {
        return presenterToId.get(presenter)
    }

    /**
     * Removes all presenters from the storage.
     * Use this method for testing purposes only.
     */
    fun clear() {
        idToPresenter.clear()
        presenterToId.clear()
    }
}