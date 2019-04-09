const express = require('express');
const _ = require('lodash');

const MUser = require('../models/MUser');
const Offer = require('../models/Offer');
const Service = require('../models/Service');
const logger = require('../logs');
const router = express.Router();


router.use((req, res, next) => {
  next();
});

router.get('/users', async (req, res) => {
  try {
    const muser = await MUser.list();
    res.json(muser);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/user/add', async (req, res) => {
  try {
    const user = await MUser.add(req.body);
    res.json(user);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/offers', async (req, res) => {
  try {
    const books = await Offer.list();
    res.json(books);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/offers/add', async (req, res) => {
  try {
    let val = Object.assign({ userId: 1239 }, req.body);
    console.log(val);
    console.log(req);
    const offer = await Offer.add(req.body);
    res.json(offer);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/services', async (req, res) => {
    try {
      const services = await Service.list();
      res.json(services);
    } catch (err) {
      res.json({ error: err.message || err.toString() });
    }
  });

// router.get('/books/:slug', async (req, res) => {
//   try {
//     const book = await Book.getBySlug({ slug: req.params.slug });
//     res.json(book);
//   } catch (err) {
//     res.json({ error: err.message || err.toString() });
//   }
// });

// router.get('/get-chapter-detail', async (req, res) => {
//   try {
//     const { bookSlug, chapterSlug } = req.query;
//     const chapter = await Chapter.getBySlug({
//       bookSlug,
//       chapterSlug,
//       userId: req.user && req.user.id,
//       isAdmin: req.user && req.user.isAdmin,
//     });
//     res.json(chapter);
//   } catch (err) {
//     res.json({ error: err.message || err.toString() });
//   }
// });

// router.get('/get-table-of-contents', async (req, res) => {
//   try {
//     const book = await Book.findOne({ slug: req.query.slug }, 'id');
//     if (!book) {
//       throw new Error('Not found');
//     }

//     const chapters = await Chapter.find(
//       { bookId: book.id, order: { $gt: 1 } },
//       'sections title slug',
//     ).sort({ order: 1 });

//     res.json(chapters);
//   } catch (err) {
//     res.json({ error: err.message || err.toString() });
//   }
// });

// router.get('/get-book-reviews', async (req, res) => {
//   try {
//     const reviewDoc = await Review.findOne({ bookSlug: req.query.slug }, 'reviews').lean();
//     const reviews = _.sortBy(reviewDoc.reviews, 'order');
//     res.json(reviews);
//   } catch (err) {
//     res.json({ error: err.message || err.toString() });
//   }
// });

// router.get('/get-tutorials', async (req, res) => {
//   try {
//     const tutorialDoc = await Tutorial.findOne({ bookSlug: req.query.slug }, 'tutorials').lean();
//     const tutorials = _.sortBy(tutorialDoc.tutorials, 'order');
//     res.json(tutorials);
//   } catch (err) {
//     res.json({ error: err.message || err.toString() });
//   }
// });

// router.post('/subscribe-to-tutorials', async (req, res) => {
//   const { email } = req.body;
//   if (!email) {
//     res.json({ error: 'Email is required' });
//     return;
//   }

//   try {
//     await subscribe({ email, listName: 'tutorials' });
//     res.json({ subscribed: 1 });
//   } catch (err) {
//     res.json({ error: err.message || err.toString() });
//   }
// });


module.exports = router;
